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
      Directive("text\n") :: Directive("global main") ::
      Label("main") :: pushlr :: statGeneration ::: (Move(r0, zero) ::
      poppc :: ltorg :: Nil)

    if (BuiltInFunctions.printFlag) {
      output = output ::: LabelData("\n") ::
        BuiltInFunctions.printString() ::: LabelData("\n") ::
        BuiltInFunctions.printInt() :::
        LabelData("\n") :: BuiltInFunctions.println()
    }

    if (BuiltInFunctions.arithmeticFlag) {
      output = output ::: LabelData("\n") ::
        BuiltInFunctions.printOverflowError() ::: LabelData("\n") ::
        BuiltInFunctions.printRuntimeError()
    }

    output

  }

  def generateStatement(statement: StatNode): List[Instruction] = {

    statement match {

      case stat: DeclarationNode => generateDeclaration(stat)
      case stat: AssignmentNode => generateAssignment(stat)
      case stat: SkipStatNode => Nil
      case stat: ExitNode => generateExit(stat)
      case SequenceNode(fstStat, sndStat) =>
        generateStatement(fstStat) ::: generateStatement(sndStat)
      case PrintNode(value) => genericPrint(value, lnFlag = false)
      case PrintlnNode(value) => genericPrint(value, lnFlag = true)
    }
  }

  def genericPrint(value: ExprNode, lnFlag: Boolean): List[Instruction] = {
    BuiltInFunctions.printFlag = true
    Labels.addDataMsgLabel("\\0", "p_print_ln")
    Labels.addDataMsgLabel("%d\\0", "p_print_int")
    Labels.addDataMsgLabel("true\\0", "p_print_bool_t")
    Labels.addDataMsgLabel("false\\0", "p_print_bool_f")
    Labels.addDataMsgLabel("%.*s\\0", "p_print_string")

    val printLink =
      if (value.getType.isEquivalentTo(IntTypeNode())) {
        BranchLink("p_print_int")
      } else BranchLink("p_print_string")

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
      case _ => throw new UnsupportedOperationException("generateAssignment")


    }
  }

  def generateExit(exit: ExitNode): List[Instruction] = {
    generateExpression(exit.exitCode) ::: (BranchLink("exit") :: Nil)
  }

  def generateAssignmentRHS(rhs: AssignmentRightNode): List[Instruction] = {
    rhs match {
      case rhs: ExprNode => generateExpression(rhs)
      case _ => throw new UnsupportedOperationException("generate Assignment " +
        "right")
    }
  }

  def generateExpression(expr: ExprNode): List[Instruction] = {

    expr match {
      case expr: IdentNode => Move(r0, StackPointerReference(AssemblyStack2
        .getOffsetForIdentifier(expr))) :: Nil
      case expr: ArrayElemNode => throw new
          UnsupportedOperationException("Generate ArrayElemnode")
      case expr: UnaryOperationNode => generateUnaryOperation(expr)
      case expr: BinaryOperationNode => generateBinaryOperation(expr)
      case IntLiteralNode(value) => Load(r0, LoadImmNum(value)) :: Nil
      case BoolLiteralNode(value) => Move(r0, ImmNum(if (value) 1 else 0)) ::
        Nil
      case CharLiteralNode(value) => Move(r0, ImmNum(value)) :: Nil
      case StringLiteralNode(value) => Labels.addMessageLabel(value); Load(r0, LabelOp(Labels.getMessageLabel)) :: Nil
      case expr: PairLiteralNode => throw new
          UnsupportedOperationException("generate pair literal node")
      case _ => throw new
          UnsupportedOperationException("generate expr catch all")

    }

  }

  def generateUnaryOperation(unOpNode: UnaryOperationNode): List[Instruction]
  = {

    //todo for arithmetic instructions, check for overflow / underflow

    unOpNode match {

      case LogicalNotNode(argument) => generateExpression(argument) :::
        (Compare(r0, zero) ::
          Load(r0, loadZero, NE) ::
          Load(r0, LoadImmNum(1), EQ) :: Nil)
      case NegationNode(argument) => generateExpression(argument) :::
        (ReverseSubNoCarry(r0, r0, zero) :: Nil)
      case LenNode(argument) => throw new
          UnsupportedOperationException("generate len")
      case OrdNode(argument) => Nil // Don't need to do anything
      case ChrNode(argument) => Nil // Don't need to do anything

    }

  }

  def generateBinaryOperation(binOpNode: BinaryOperationNode):
  List[Instruction] = {

    binOpNode match {

      case binOp: ArithmeticBinaryOperationNode =>
        generateArithmeticBinaryOperation(binOp)
      case binOp: OrderComparisonOperationNode =>
        generateOrderComparisonOperation(binOp)
      case binOp: ComparisonOperationNode => generateComparisonOperation(binOp)
      case binOp: BooleanBinaryOperationNode =>
        generateBooleanBinaryOperation(binOp)
    }

  }

  def generateArithmeticBinaryOperation(arithBinOp:
                                        ArithmeticBinaryOperationNode):
  List[Instruction] = {
    BuiltInFunctions.arithmeticFlag = true
    val mainInstructions: List[Instruction] = arithBinOp match {
      case arithBin: MulNode => Pop(r1) ::
        SMull(r0, r1, r0, r1) :: Nil
      case arithBin: DivNode => Pop(r1) ::
        SDiv(r0, r0, r1) :: Nil
      case arithBin: ModNode => throw new UnsupportedOperationException("generate mod")

      case arithBin: PlusNode => Pop(r1) ::
        Add(r0, r0, r1) :: Nil
      case arithBin: MinusNode => Pop(r1) ::
        Sub(r0, r0, r1) :: Nil
    }

    generateExpression(arithBinOp.rightExpr) :::
      (Push(r0) :: Nil) :::
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

    generateExpression(orderOp.rightExpr) ::: (Push(r0) :: Nil) :::
      generateExpression(orderOp.leftExpr) :::
      (Pop(r1) :: Compare(r0, r1) :: Nil) :::
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

    generateExpression(compOp.rightExpr) ::: (Push(r0) :: Nil) :::
      generateExpression(compOp.leftExpr) :::
      (Pop(r1) :: Compare(r0, r1) :: Nil) :::
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
      (Push(r0) :: Nil) :::
      generateExpression(boolOp.leftExpr) :::
      (Pop(r1) :: Nil) :::
      mainInstruction

  }


}
