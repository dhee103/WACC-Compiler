import Condition._
import Constants._

object CodeGen {

  //use the above e.g when pushing sp or subbing from the stack to make space

  val spReference = StackPointerReference(0)
  //use when doing stuff with things on the stack e.g addition with a value
  // on the stack

  //  var stack = new AssemblyStack()

  def generateProgramCode(prog: ProgNode): List[Instruction] = {

    //    Initialise labels with builtin functions
    Labels.addDataMsgLabel("OverflowError: the result is too small/large to " +
      "store in a 4-byte signed-integer.", "p_throw_overflow_error")

    AssemblyStack3.createNewScope(prog.symbols.head)
    val statement: StatNode = prog.statChild

    val statGeneration = generateStatement(statement)

    var output = (Directive("data\n") :: Nil) :::
      Labels.printMsgMap() :::
      Labels.printDataMsgMap() :::
      Directive("text\n") ::
      Directive("global main") ::
      Label("main") ::
      pushlr ::
      Push(fp) ::
      Move(fp, sp) ::
      Sub(sp, sp, ImmNum(WORD_SIZE * prog.scopeSizes.head)) ::
      statGeneration :::
      Add(sp, sp, ImmNum(WORD_SIZE * prog.scopeSizes.head)) ::
      (Move(r0, zero) ::
      Pop(fp) ::
      poppc :: ltorg :: Nil)

    if (PredefinedFunctions.printFlag) {
      output = output ::: LabelData("\n") ::
        PredefinedFunctions.printString() ::: LabelData("\n") ::
        PredefinedFunctions.printInt() ::: LabelData("\n") ::
        PredefinedFunctions.printBool() ::: LabelData("\n") ::
        PredefinedFunctions.println() ::: LabelData("\n") ::
        PredefinedFunctions.printReference()
    }

    if (PredefinedFunctions.arithmeticFlag) {
      output = output ::: LabelData("\n") ::
        PredefinedFunctions.overflowError() ::: LabelData("\n") ::
        PredefinedFunctions.runtimeError()
    }

    if (PredefinedFunctions.freePairFlag) {
      output = output ::: LabelData("\n") ::
      PredefinedFunctions.freePair() ::: LabelData("\n") ::
      PredefinedFunctions.runtimeError()
    }

    if (PredefinedFunctions.nullPointerFlag) {
      output = output ::: LabelData("\n") ::
      PredefinedFunctions.checkNullPointer() ::: LabelData("\n") ::
      PredefinedFunctions.runtimeError()
    }

    output

  }

  def generateStatement(statement: StatNode): List[Instruction] = {

    statement match {
      case stat: SkipStatNode => Nil
      case stat: DeclarationNode =>
        generateDeclaration(stat)
      case stat: AssignmentNode =>
        generateAssignment(stat)
      case stat: ReadNode =>
        throw new UnsupportedOperationException("generateReadNode not implemented")
      case FreeNode(variable) =>
        PredefinedFunctions.freePairFlag = true
        Labels.addDataMsgLabel("NullReferenceError: dereference a null reference\\n\\0", "free_pair")
        generateExpression(variable) :::
        BranchLink("p_free_pair") :: Nil
//        TODO: do for an array
        // TODO: should whatever variable points to be zeroed out?
      case stat: ReturnNode =>
        throw new UnsupportedOperationException("generateReturnNode not implemented")
      case stat: ExitNode =>
        generateExit(stat)
      case PrintNode(value) =>
        genericPrint(value, lnFlag = false)
      case PrintlnNode(value) =>
        genericPrint(value, lnFlag = true)
      case stat: IfNode =>
        generateIf(stat)
      case stat: WhileNode =>
        generateWhile(stat)
      case stat: NewBeginNode =>
        generateNewBegin(stat)
      case SequenceNode(fstStat, sndStat) =>
        generateStatement(fstStat) ::: generateStatement(sndStat)
    }
  }

  def genericPrint(value: ExprNode, lnFlag: Boolean): List[Instruction] = {
    PredefinedFunctions.printFlag = true
    Labels.addDataMsgLabel("\\0", "p_print_ln")
    Labels.addDataMsgLabel("%d\\0", "p_print_int")
    Labels.addDataMsgLabel("true\\0", "p_print_bool_t")
    Labels.addDataMsgLabel("false\\0", "p_print_bool_f")
    Labels.addDataMsgLabel("%.*s\\0", "p_print_string")
    Labels.addDataMsgLabel("%p\\0", "p_print_reference")

    val printLink = value.getType match {
      case t if t.isEquivalentTo(IntTypeNode()) => BranchLink("p_print_int")
      case t if t.isEquivalentTo(StringTypeNode()) => BranchLink("p_print_string")
      case t if t.isEquivalentTo(BoolTypeNode()) => BranchLink("p_print_bool")
      case t if t.isEquivalentTo(CharTypeNode()) => BranchLink("putchar")
      case _ => BranchLink("p_print_reference")


    }
    generateExpression(value) ::: (printLink ::
      (if (lnFlag) BranchLink("p_print_ln") :: Nil else Nil))
  }

  def generateDeclaration(decl: DeclarationNode): List[Instruction] = {
    val ident = decl.identifier
    val rhs = decl.rhs

    val offset = AssemblyStack3.getOffsetFor(ident)

    generateAssignmentRHS(rhs) :::
      Store(r0, FramePointerReference(offset)) ::
      Nil
  }

  def generateAssignment(assignment: AssignmentNode): List[Instruction] = {
    val lhs = assignment.lhs
    val rhs = assignment.rhs

    lhs match {
      case id: IdentNode =>
        val offset = AssemblyStack3.getOffsetFor(id)
        generateAssignmentRHS(rhs) :::
          Store(r0, FramePointerReference(offset)) ::
          Nil
      case fst: FstNode =>
//        TODO: Will need to sort out the offset; I think it's offset + 4 in the RegisterStackReference(sp, 4)
        PredefinedFunctions.nullPointerFlag = true
        generateAssignmentRHS(rhs) :::
        Push(r0) ::
        Load(r0, RegisterStackReference(sp, 4)) ::
        BranchLink("p_check_null_pointer") ::
        Add(r0, r0, ImmNum(0)) :: //        Redundant?
        Push(r0) ::
        Load(r0, RegisterStackReference(r0)) ::
        BranchLink("free") ::
        Move(r0, ImmNum(4)) ::
        BranchLink("malloc") ::
        Pop(r1) ::
        Store(r0, RegisterStackReference(r1)) ::
        Move(r1, r0) ::
        Pop(r0) ::
        Store(r0, RegisterStackReference(r1)) :: Nil

      case _ => throw new UnsupportedOperationException("generateAssignment")
    }
  }

  def generateExit(exit: ExitNode): List[Instruction] = {
    generateExpression(exit.exitCode) ::: (BranchLink("exit") :: Nil)
  }

  def generateIf(ifStat: IfNode): List[Instruction] = {
    val condition = generateExpression(ifStat.condition)
    val setUpThenFrame = AssemblyStack3.createNewScope(ifStat.symbols.head)
    val thenBranch = generateStatement(ifStat.thenStat)
    val closeThenFrame = AssemblyStack3.destroyNewestScope()
    val setUpElseFrame = AssemblyStack3.createNewScope(ifStat.symbols(1))
    val elseBranch = generateStatement(ifStat.elseStat)
    val closeElseFrame = AssemblyStack3.destroyNewestScope()

    val (elseBranchLabel, endIfLabel) = Labels.getIfLabels

    condition :::
    Compare(r0, ImmNum(0)) ::
    StandardBranch(elseBranchLabel, EQ) ::
    setUpThenFrame :::
    thenBranch :::
    closeThenFrame :::
    StandardBranch(endIfLabel) ::
    Label(elseBranchLabel) ::
    setUpElseFrame :::
    elseBranch :::
    closeElseFrame :::
    Label(endIfLabel) :: Nil

  }

  def generateWhile(whileStat: WhileNode): List[Instruction] = {
    val condition = generateExpression(whileStat.condition)
    val setUpFrame = AssemblyStack3.createNewScope(whileStat.symbols.head)
    val loopBody = generateStatement(whileStat.loopBody)
    val closeFrame = AssemblyStack3.destroyNewestScope()

    val (whileStart, whileEnd) = Labels.getWhileLabels

    Label(whileStart) ::
    condition :::
    Compare(r0, ImmNum(0)) ::
    StandardBranch(whileEnd, EQ) ::
    setUpFrame :::
    loopBody :::
    closeFrame :::
    StandardBranch(whileStart) ::
    Label(whileEnd) :: Nil

  }

  def generateNewBegin(begin: NewBeginNode): List[Instruction] = {
    val setUpStack = AssemblyStack3.createNewScope(begin.symbols.head)
    val statOutput = generateStatement(begin.body)
    val closeScope = AssemblyStack3.destroyNewestScope()

    setUpStack ::: statOutput ::: closeScope
  }


  def generateFstNode(exprChild: ExprNode): List[Instruction] = {
    PredefinedFunctions.nullPointerFlag = true
    generateExpression(exprChild) :::
    BranchLink("p_check_null_pointer") ::
    Load(r0, RegisterStackReference(r0)) ::
    Load(r0, RegisterStackReference(r0)) :: Nil

  }

  def generateSndNode(exprChild: ExprNode): List[Instruction] = {
    PredefinedFunctions.nullPointerFlag = true
    generateExpression(exprChild) :::
    BranchLink("p_check_null_pointer") ::
    Load(r0, RegisterStackReference(r0, 4)) ::
    Load(r0, RegisterStackReference(r0)) :: Nil
  }

  def generateAssignmentRHS(rhs: AssignmentRightNode): List[Instruction] = {
    rhs match {
      case rhs: ExprNode => generateExpression(rhs)
      case NewPairNode(fstElem, sndElem) => generateNewPairNode(fstElem, sndElem)
      case FstNode(exprChild) => generateFstNode(exprChild)
      case SndNode(exprChild) => generateSndNode(exprChild)
//        case PairElemNode => Labels.addDataMsgLabel(msg_p_check_null_pointer)
      case _ => throw new UnsupportedOperationException("generate Assignment " +
        "right")
    }
  }

  def generateNewPairNode(fstElem: ExprNode, sndElem: ExprNode): List[Instruction] = {
    generateExpression(fstElem) :::
    generateNewPairElem(fstElem) :::
    generateExpression(sndElem) :::
    generateNewPairElem(sndElem) :::
    (Move(r0, ImmNum(8)) ::
      BranchLink("malloc") ::
      Pop(r1) ::
      Pop(r2) ::
      Store(r2, RegisterStackReference(r0)) ::
      Store(r1, RegisterStackReference(r0, 4)) :: Nil)
  }

  def generateNewPairElem(elem: ExprNode): List[Instruction] = {
    Push(r0) ::
    Move(r0, ImmNum(4)) ::
    BranchLink("malloc") ::
    Pop(r1) ::
    Store(r1, RegisterStackReference(r0)) ::
    Push(r0) :: Nil
  }

  def generateExpression(expr: ExprNode): List[Instruction] = {

    expr match {
      case expr: IdentNode =>
        Load(r0, FramePointerReference(AssemblyStack3.getOffsetFor(expr))) :: Nil
      case expr: ArrayElemNode => throw new
          UnsupportedOperationException("Generate ArrayElemnode")
      case expr: UnaryOperationNode => generateUnaryOperation(expr)
      case expr: BinaryOperationNode => generateBinaryOperation(expr)
      case IntLiteralNode(value) => Load(r0, LoadImmNum(value)) :: Nil
      case BoolLiteralNode(value) => Move(r0, ImmNum(if (value) 1 else 0)) :: Nil
      case CharLiteralNode(value) => Load(r0, LoadImmNum(value)) :: Nil
      case StringLiteralNode(value) => Labels.addMessageLabel(value); Load(r0, LabelOp(Labels.getMessageLabel)) :: Nil
      case expr: PairLiteralNode => Move(r0, ImmNum(0)) :: Nil
      case _ => throw new
          UnsupportedOperationException("generate expr catch all")

    }

  }

  def generateUnaryOperation(unOpNode: UnaryOperationNode): List[Instruction] = {

    val mainInstruction = unOpNode match {
      case LogicalNotNode(argument) =>
        generateExpression(argument) :::
        (Compare(r0, zero) ::
          Load(r0, loadZero, NE) ::
          Load(r0, LoadImmNum(1), EQ) :: Nil)
      case NegationNode(argument) =>
        generateExpression(argument) :::
        (ReverseSubNoCarry(r0, r0, zero) :: Nil)
      case LenNode(argument) => throw new
          UnsupportedOperationException("generate len")
      case OrdNode(argument) =>
        generateExpression(argument) // Don't need to do anything else
      case ChrNode(argument) =>
        generateExpression(argument) // Don't need to do anything else
    }

    mainInstruction ::: BranchLink("p_throw_overflow_error", VS) :: Nil

  }

  def generateBinaryOperation(binOpNode: BinaryOperationNode):
  List[Instruction] = {

    binOpNode match {
      case binOp: ArithmeticBinaryOperationNode =>
        generateArithmeticBinaryOperation(binOp)
      case binOp: OrderComparisonOperationNode =>
        generateOrderComparisonOperation(binOp)
      case binOp: ComparisonOperationNode =>
        generateComparisonOperation(binOp)
      case binOp: BooleanBinaryOperationNode =>
        generateBooleanBinaryOperation(binOp)
    }

  }

  def generateArithmeticBinaryOperation(arithBinOp:
                                        ArithmeticBinaryOperationNode):
  List[Instruction] = {
    PredefinedFunctions.arithmeticFlag = true
    val mainInstructions: List[Instruction] = arithBinOp match {
      case arithBin: MulNode =>
        Pop(r1) ::
        SMull(r0, r1, r0, r1) :: Nil
      case arithBin: DivNode =>
        Pop(r1) ::
        SDiv(r0, r0, r1) :: Nil
      case arithBin: ModNode =>
        throw new UnsupportedOperationException("generate mod")
      case arithBin: PlusNode =>
        Pop(r1) ::
        Add(r0, r0, r1) :: Nil
      case arithBin: MinusNode =>
        Pop(r1) ::
        Sub(r0, r0, r1) :: Nil
    }

    generateExpression(arithBinOp.rightExpr) :::
    Push(r0) ::
    generateExpression(arithBinOp.leftExpr) :::
    mainInstructions :::
    BranchLink("p_throw_overflow_error", VS) :: Nil
  }

  def generateOrderComparisonOperation(orderOp: OrderComparisonOperationNode)
  : List[Instruction] = {

    val comparisonInstrs: List[Instruction] = orderOp match {
      case orderBin: GreaterThanNode =>
        Load(r0, LoadImmNum(1), GT) :: Load(r0, LoadImmNum(0), LE) :: Nil
      case orderBin: GreaterEqualNode =>
        Load(r0, LoadImmNum(1), GE) :: Load(r0, LoadImmNum(0), LT) :: Nil
      case orderBin: LessThanNode =>
        Load(r0, LoadImmNum(1), LT) :: Load(r0, LoadImmNum(0), GE) :: Nil
      case orderBin: LessEqualNode =>
        Load(r0, LoadImmNum(1), LE) :: Load(r0, LoadImmNum(0), GT) :: Nil
    }

    generateExpression(orderOp.rightExpr) :::
    Push(r0) ::
    generateExpression(orderOp.leftExpr) :::
    Pop(r1) ::
    Compare(r0, r1) ::
    comparisonInstrs

  }

  def generateComparisonOperation(compOp: ComparisonOperationNode):
  List[Instruction] = {

    val comparisonInstrs: List[Instruction] = compOp match {
      case compBin: DoubleEqualNode =>
        Load(r0, LoadImmNum(1), EQ) :: Load(r0, LoadImmNum(0), NE) :: Nil
      case compBin: NotEqualNode =>
        Load(r0, LoadImmNum(1), NE) :: Load(r0, LoadImmNum(0), EQ) :: Nil
    }

    generateExpression(compOp.rightExpr) :::
    Push(r0) ::
    generateExpression(compOp.leftExpr) :::
    Pop(r1) ::
    Compare(r0, r1) ::
    comparisonInstrs

  }

  def generateBooleanBinaryOperation(boolOp: BooleanBinaryOperationNode):
  List[Instruction] = {

    val mainInstruction = boolOp match {
      case boolBin: LogicalAndNode =>
        And(r0, r0, r1) :: Nil

      case boolBin: LogicalOrNode =>
        Orr(r0, r0, r1) :: Nil
    }

    generateExpression(boolOp.rightExpr) :::
    Push(r0) ::
    generateExpression(boolOp.leftExpr) :::
    Pop(r1) ::
    mainInstruction

  }

}
