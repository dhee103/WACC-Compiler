import Condition._

object CodeGen {

  val lr = LinkRegister()
  val r0  = ResultRegister()
  val r1 = R1()
  val pc  = ProgramCounter()


  val sp = StackPointer()
  //use the above e.g when pushing sp or subbing from the stack to make space

  val spReference = StackReference(0)
  //use when doing stuff with things on the stack e.g addition with a value on the stack

  val zero = ImmNum(0)
  val loadZero = LoadImmNum(0)
  val pushlr = Push(lr)
  val poppc = Pop(pc)
  val ltorg = Directive("ltorg")

  var stack = new AssemblyStack()

  def generateProgramCode(prog: ProgNode): List[Instruction] = {

    val statement: StatNode = prog.statChild

    val statGeneration = generateStatement(statement)

    val output = Labels.printDataMsgMap() :::
    Directive(".text") :: Directive("\n.global main") ::
    Label("main") :: pushlr :: statGeneration ::: (Move(r0, zero) ::
    poppc :: ltorg ::  Nil)

    if (BuiltInFunctions.printFlag) {
      output ::: LabelData("\n") :: BuiltInFunctions.printInt() ::: LabelData("\n") :: BuiltInFunctions.println()
    } else {
      output
    }

  }

  def generateStatement(statement: StatNode): List[Instruction] = {

    statement match {

      case stat: DeclarationNode          => generateDeclaration(stat)
      case stat: AssignmentNode           => generateAssignment(stat)
      case stat: SkipStatNode             => Nil
      case stat: ExitNode                 => generateExit(stat)
      case SequenceNode(fstStat, sndStat) => generateStatement(fstStat) ::: generateStatement(sndStat)
      // case PrintNode(value)
      case PrintlnNode(value)  =>
        BuiltInFunctions.printFlag = true
        Labels.addDataMsgLabel("\\0", "p_print_ln")
        Labels.addDataMsgLabel("%d\\0", "p_print_int")
        Labels.addDataMsgLabel("true\\0", "p_print_bool_t")
        Labels.addDataMsgLabel("false\\0", "p_print_bool_f")
//        add in all labels
        BranchLink("p_print_int") :: BranchLink("p_print_ln") :: Nil
    }
  }

  def generateDeclaration(decl: DeclarationNode): List[Instruction] = {
    null
  }

  def generateAssignment(assignment: AssignmentNode): List[Instruction] = {
    null
  }

  def generateExit(exit: ExitNode): List[Instruction] = {
    val exitCode: Operand = exit.exitCode match {
      case IntLiteralNode(value) => LoadImmNum(value)
      case _ => throw new UnsupportedOperationException("anything that could go to an int")
    }

    List[Instruction](Load(r0, exitCode), BranchLink("exit"))
  }

  def generateExpression(expr: ExprNode): List[Instruction] = {

    expr match {
      case expr: IdentNode            => Move(r0, StackReference(stack.getOffsetForIdentifier(expr))) :: Nil
      case expr: ArrayElemNode        => null
      case expr: UnaryOperationNode   => generateUnaryOperation(expr)
      case expr: BinaryOperationNode  => generateBinaryOperation(expr)
      case IntLiteralNode(value)      => Move(r0, ImmNum(value)) :: Nil
      case BoolLiteralNode(value)     => Move(r0, ImmNum(if (value) 1 else 0 )) :: Nil
      case CharLiteralNode(value)     => Move(r0, ImmNum(value)) :: Nil
      case StringLiteralNode(value)   => Labels.addMessageLabel(value); Load(r0, DataCall(Labels.getMessageLabel)) :: Nil
      case expr: PairLiteralNode      => null
      case _                          => null
    }

  }

  def generateUnaryOperation(unOpNode: UnaryOperationNode): List[Instruction] = {

    //todo for arithmetic instructions, check for overflow / underflow

    unOpNode match {

      case LogicalNotNode(argument) =>  (generateExpression(argument)) ::: ((Compare(r0, zero))
                                        :: (Load(r0, loadZero, NE)) :: (Load(r0, LoadImmNum(1), EQ)) :: Nil)
      case NegationNode(argument)   => (generateExpression(argument)) ::: (ReverseSubNoCarry(r0, r0, zero) :: Nil)
      case LenNode(argument)        =>
      case OrdNode(argument)        => //donothing
      case ChrNode(argument)        => //donothing

    }

    null

  }

  def generateBinaryOperation(binOpNode: BinaryOperationNode): List[Instruction] = {

    binOpNode match {

      case binOp: ArithmeticBinaryOperationNode => generateArithmeticBinaryOperation(binOp)
      case binOp: OrderComparisonOperationNode  => generateOrderComparisonOperation(binOp)
      case binOp: ComparisonOperationNode       => generateComparisonOperation(binOp)
      case binOp: BooleanBinaryOperationNode    => generateBooleanBinaryOperation(binOp)
    }

    null

  }

  def generateArithmeticBinaryOperation(arithBinOp: ArithmeticBinaryOperationNode): List[Instruction] = {

    val mainInstructions: List[Instruction] = arithBinOp match {
      case arithBin: MulNode =>  Move(r1, spReference) :: SMull(r0, r1, r0, r1) :: Nil
      case arithBin: DivNode => Move (r1, spReference) :: SDiv(r0, r1, r0) :: Nil
      case arithBin: ModNode => null
      case arithBin: PlusNode => Add(r0, r0, spReference) :: Nil
      case arithBin: MinusNode => Sub(r0, r0, spReference) :: Nil
    }

    generateExpression(arithBinOp.leftExpr) :::
                                (Push(r0) :: Nil) ::: generateExpression(arithBinOp.rightExpr) :::
                                mainInstructions ::: (Add(sp, sp, ImmNum(4))
      :: BranchLink("p_throw_overflow_error", VS) :: Nil)
  }

  def generateOrderComparisonOperation(orderOp: OrderComparisonOperationNode): List[Instruction] = {

    orderOp match {

    case orderBin: GreaterThanNode => null
    case orderBin: GreaterEqualNode => null
    case orderBin: LessThanNode => null
    case orderBin: LessEqualNode => null

    }
  }

  def generateComparisonOperation(compOp: ComparisonOperationNode): List[Instruction] = {

    compOp match {

      case compBin: DoubleEqualNode => null
      case compBin: NotEqualNode => null
    }

  }

  def generateBooleanBinaryOperation(boolOp: BooleanBinaryOperationNode): List[Instruction] = {

    boolOp match {

      case boolBin: LogicalAndNode => null
      case boolBin: LogicalOrNode => null
    }
  }


}
