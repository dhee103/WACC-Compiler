import Condition._
import Shift._
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
    Labels.addDataMsgLabel("%.*s\\0", "p_print_string")
    Labels.addDataMsgLabel("\\0", "p_print_ln")
    Labels.addDataMsgLabel("NullReferenceError: dereference a null reference\\n\\0", "null_check")

//    remove


    AssemblyStack3.createNewScope(prog.symbols.head)
    val statement: StatNode = prog.statChild

    val statGeneration = generateStatement(statement)

    var output = (Directive("data\n") :: Nil) :::
      Labels.printMsgMap() :::
      Labels.printDataMsgMap() :::
      Directive("text\n") ::
      Directive("global main") ::
      Label("main") ::
      Push(lr) ::
      Push(fp) ::
      Move(fp, sp) ::
      Sub(sp, sp, ImmNum(WORD_SIZE * prog.scopeSizes.head)) ::
      statGeneration :::
      Add(sp, sp, ImmNum(WORD_SIZE * prog.scopeSizes.head)) ::
      Move(r0, zero) ::
      Pop(fp) ::
      Pop(pc) ::
      Directive("ltorg") :: Nil

    if (PredefinedFunctions.printFlag) {
      output = output ::: LabelData("\n") ::
        PredefinedFunctions.printString() ::: LabelData("\n") ::
        PredefinedFunctions.printInt() ::: LabelData("\n") ::
        PredefinedFunctions.printBool() ::: LabelData("\n") ::
        PredefinedFunctions.println() ::: LabelData("\n") ::
        PredefinedFunctions.printReference()
    }

    if (PredefinedFunctions.arithmeticFlag) {
      PredefinedFunctions.runtimeFlag = true

      output = output ::: LabelData("\n") ::
        PredefinedFunctions.overflowError() ::: LabelData("\n") :: Nil
    }

    if (PredefinedFunctions.divisionFlag) {
      PredefinedFunctions.runtimeFlag = true

      output = output ::: LabelData("\n") ::
      PredefinedFunctions.checkDivByZero() ::: LabelData("\n") :: Nil
    }

    if (PredefinedFunctions.freePairFlag) {
      PredefinedFunctions.runtimeFlag = true

      output = output ::: LabelData("\n") ::
      PredefinedFunctions.freePair() ::: LabelData("\n") :: Nil
    }

    if (PredefinedFunctions.nullPointerFlag) {
      PredefinedFunctions.runtimeFlag = true

      output = output ::: LabelData("\n") ::
      PredefinedFunctions.checkNullPointer() ::: LabelData("\n") :: Nil
    }

    if (PredefinedFunctions.checkArrayBoundsFlag) {
      PredefinedFunctions.runtimeFlag = true

      output = output ::: LabelData("\n") ::
        PredefinedFunctions.checkArrayBounds()
    }

    if (PredefinedFunctions.runtimeFlag) {
      output = output ::: LabelData("\n") ::
      PredefinedFunctions.runtimeError()

      if (!PredefinedFunctions.printFlag) {
        output = output ::: LabelData("\n") ::
        PredefinedFunctions.printString() ::: LabelData("\n") ::
        PredefinedFunctions.println()
      }
    }

    if (PredefinedFunctions.readCharFlag) {
      output = output ::: LabelData("\n") :: PredefinedFunctions.readChar()
    }

    if (PredefinedFunctions.readIntFlag) {
      output = output ::: LabelData("\n") :: PredefinedFunctions.readInt()
    }

    output

  }

  def generateReadNode(variable: AssignmentLeftNode): List[Instruction] = {
    val offset = variable match {
      case id: IdentNode => AssemblyStack3.getOffsetFor(id)
      case _ => 0
    }

    variable match {
      case v if v.getType.isEquivalentTo(CharTypeNode()) =>
        Labels.addDataMsgLabel(" %c\\0", "p_read_char")
        PredefinedFunctions.readCharFlag = true
        Add(r0, fp, ImmNum(offset)) :: BranchLink("p_read_char") :: Nil
      case v if v.getType.isEquivalentTo(IntTypeNode()) =>
        Labels.addDataMsgLabel("%d\\0", "p_read_int")
        PredefinedFunctions.readIntFlag = true
        Add(r0, fp, ImmNum(offset)) :: BranchLink("p_read_int") :: Nil
    }
  }

  def generateStatement(statement: StatNode): List[Instruction] = {

    statement match {
      case stat: SkipStatNode => Nil
      case stat: DeclarationNode =>
        generateDeclaration(stat)
      case stat: AssignmentNode =>
        generateAssignment(stat)
      case ReadNode(variable) =>

        generateReadNode(variable)
//        throw new UnsupportedOperationException("generateReadNode not implemented")
      case FreeNode(variable) =>
        PredefinedFunctions.freePairFlag = true
//        Labels.addDataMsgLabel("NullReferenceError: dereference a null reference\\n\\0", "null_check")

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
//    Labels.addDataMsgLabel("\\0", "p_print_ln")
    Labels.addDataMsgLabel("%d\\0", "p_print_int")
    Labels.addDataMsgLabel("true\\0", "p_print_bool_t")
    Labels.addDataMsgLabel("false\\0", "p_print_bool_f")
//    Labels.addDataMsgLabel("%.*s\\0", "p_print_string")
    Labels.addDataMsgLabel("%p\\0", "p_print_reference")

    val printLink = value.getType match {
      case t if t.isEquivalentTo(IntTypeNode()) => BranchLink("p_print_int")
      case t if t.isEquivalentTo(StringTypeNode()) => BranchLink("p_print_string")
      case t if t.isEquivalentTo(BoolTypeNode()) => BranchLink("p_print_bool")
      case t if t.isEquivalentTo(CharTypeNode()) => BranchLink("putchar")
//      case t if t.isEquivalentTo(ArrayTypeNode(AnyTypeNode())) =>
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

  def generateAssignmentPair(expr: ExprNode, offset: Int = 0): List[Instruction] = {
    val load =
      if (offset != 0) Load(r0, RegisterStackReference(r0, offset))
      else Load(r0, RegisterStackReference(r0))  // r0= address of fst elem

    Push(r0) ::
    generateExpression(expr) ::: // r0 = address of pair
    BranchLink("p_check_null_pointer") ::
    load ::
    Pop(r1) :: // r1 = value on rhs
    Store(r1, RegisterStackReference(r0)) :: Nil
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

      case FstNode(expr) =>
        PredefinedFunctions.nullPointerFlag = true
        generateAssignmentRHS(rhs) ::: // r0 = value on rhs
        generateAssignmentPair(expr)

      case SndNode(expr) =>
        PredefinedFunctions.nullPointerFlag = true
        generateAssignmentRHS(rhs) ::: // r0 = value on rhs
        generateAssignmentPair(expr, WORD_SIZE)

      case arr: ArrayElemNode =>
//        TODO: Improve the efficiency of this
        generateExpression(arr) :::     //        r0 = LHS
        Move(r1, r0) :: //r1 = array index i.e. s[1]
//        TODO: check valid call
        generateAssignmentRHS(rhs) :::  // r0 = RHS
//        [r1] = [r0] -- store?
        Store(r0, RegisterStackReference(r1)) ::
        Move (r0, r1) :: Nil


//        throw new UnsupportedOperationException(s"ArraysAssignment $arr")
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

//  TODO: Check this works with things which aren't int's
  def generateStoreArrayELem(value: (ExprNode, Int)): List[Instruction] = {
    // Should do this Load(r0, LoadImmNum(value)) :: Store(r0, [r2, #4 * index + 1])
    generateExpression(value._1) :::
    Store(r0, RegisterStackReference(r3, WORD_SIZE * (value._2 + 1))) :: Nil
  }

  def generateAssignmentRHS(rhs: AssignmentRightNode): List[Instruction] = {
    rhs match {
      case rhs: ExprNode => generateExpression(rhs)
      case NewPairNode(fstElem, sndElem) => generateNewPairNode(fstElem, sndElem)
      case FstNode(exprChild) => generateFstNode(exprChild)
      case SndNode(exprChild) => generateSndNode(exprChild)
      case ArrayLiteralNode(values) =>
        val numElems = values.length
        var elemCode: List[Instruction] = Nil

        for (value <- values.zipWithIndex) {
          elemCode = elemCode ::: generateStoreArrayELem(value)
        }

        Move(r0, ImmNum(WORD_SIZE * (numElems + 1))) ::
        BranchLink("malloc") ::
        Move(r3, r0) ::
        elemCode :::
        Move(r0, ImmNum(numElems)) ::
        Store(r0, RegisterStackReference(r3)) ::
        Move(r0, r3) ::
//        Store(r0, RegisterStackReference(fp)) ::
        Nil
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
      case ArrayElemNode(identifier, index) =>
        Labels.addDataMsgLabel("ArrayIndexOutOfBoundsError: negative index\\n\\0", "negative_index")
        Labels.addDataMsgLabel("ArrayIndexOutOfBoundsError: index too large\\n\\0", "index_too_large")
        PredefinedFunctions.checkArrayBoundsFlag = true
        val offset = AssemblyStack3.getOffsetFor(identifier)

        Load(r0, RegisterStackReference(fp, offset)) :: // r0 = address of array
        Move(r4, r0) :: // r4 = address of array
        generateExpression(index(0)) ::: // r0 = first index
        BranchLink("p_check_array_bounds") :: // checks address in r4, index in r0
        Add(r4, r4, ImmNum(WORD_SIZE)) :: // moves past "size of array" stored in array
        AddShift(r4, r4, r0, LSL, 2) :: // r4 = r4 + WORD_SIZE * index
        Load(r4, RegisterStackReference(r4)) :: // r4 = actual element in array
        Move(r0, r4) :: Nil // r0 = actual element in array

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

    unOpNode match {
      case LogicalNotNode(argument) =>
        generateExpression(argument) :::
        Compare(r0, zero) ::
        Load(r0, loadZero, NE) ::
        Load(r0, LoadImmNum(1), EQ) :: Nil
      case NegationNode(argument) =>
        PredefinedFunctions.arithmeticFlag = true

        generateExpression(argument) :::
        ReverseSubNoCarry(r0, r0, zero) ::
        BranchLink("p_throw_overflow_error", VS) :: Nil
      case LenNode(argument) =>
//        TODO: Check that it's not called on anything but an array?
        generateExpression(argument) ::: // r0 = argument
        Load(r0, RegisterStackReference(r0)) :: Nil // r0 = First Element in R0 which should be array size

      case OrdNode(argument) =>
        generateExpression(argument) // Don't need to do anything else
      case ChrNode(argument) =>
        generateExpression(argument) // Don't need to do anything else
    }

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
        Labels.addDataMsgLabel("DivideByZeroError: divide or modulo by zero\\n\\0", "p_check_divide_by_zero")
        PredefinedFunctions.divisionFlag = true

        Pop(r1) ::
        BranchLink("p_check_divide_by_zero") ::
        BranchLink("__aeabi_idiv") :: Nil
      case arithBin: ModNode =>
        Labels.addDataMsgLabel("DivideByZeroError: divide or modulo by zero\\n\\0", "p_check_divide_by_zero")
        PredefinedFunctions.divisionFlag = true

        Pop(r1) ::
        BranchLink("p_check_divide_by_zero") ::
        BranchLink("__aeabi_idivmod") ::
        Move(r0, r1) ::  Nil
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
