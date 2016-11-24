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

  var stack = new AssemblyStack()

  def generateProgramCode(prog: ProgNode): List[Instruction] = {

    val statement: StatNode = prog.statChild


    (Directive(".text")) :: (Directive(".global main")) ::
    (Label("main")) :: pushlr :: generateStatement(statement) ++ (Move(r0, zero) ::
    poppc :: Nil)

  }

  def generateStatement(statement: StatNode): List[Instruction] = {

    statement match {

      case stat: DeclarationNode          => generateDeclaration(stat)
      case stat: AssignmentNode           => generateAssignment(stat)
      case stat: SkipStatNode             => Nil
      case stat: ExitNode                 => generateExit(stat)
      case SequenceNode(fstStat, sndStat) => generateStatement(fstStat) ::: generateStatement(sndStat)

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
      case arithBin: ModNode => null //todo read up of ref compiler and copy
      case arithBin: PlusNode => Add(r0, r0, spReference) :: Nil
      case arithBin: MinusNode => Sub(r0, r0, spReference) :: Nil
    }

    (generateExpression(arithBinOp.leftExpr)) :::
                                (Push(r0) :: Nil) ::: generateExpression(arithBinOp.rightExpr) :::
                                mainInstructions ::: (Add(sp, sp, ImmNum(4)) :: Nil)
  }

  def generateOrderComparisonOperation(orderOp: OrderComparisonOperationNode): List[Instruction] = {

    val mainInstructions: List[Instruction] = orderOp match {

    case orderBin: GreaterThanNode =>  Load(r0, LoadImmNum(1), GT) :: Load(r0, loadZero, LE) :: Nil
    case orderBin: GreaterEqualNode =>  Load(r0, LoadImmNum(1), GE) :: Load(r0, loadZero, LT) :: Nil
    case orderBin: LessThanNode =>  Load(r0, LoadImmNum(1), LT) :: Load(r0, loadZero, GE) :: Nil
    case orderBin: LessEqualNode =>  Load(r0, LoadImmNum(1), LE) :: Load(r0, loadZero, GT) :: Nil

    }

    (generateExpression(orderOp.rightExpr)) :::
                                (Push(r0) :: Nil) ::: generateExpression(orderOp.leftExpr) ::: (Compare(r0, spReference) :: Nil)
                                mainInstructions ::: (Add(sp, sp, ImmNum(4)) :: Nil)


  }

  def generateComparisonOperation(compOp: ComparisonOperationNode): List[Instruction] = {

    val mainInstructions: List[Instruction] = compOp match {

      case compBin: DoubleEqualNode => Load(r0, LoadImmNum(1), EQ) :: Load(r0, loadZero, NE) :: Nil
      case compBin: NotEqualNode => Load(r0, LoadImmNum(1), NE) :: Load(r0, loadZero, EQ) :: Nil
    }

    (generateExpression(compOp.leftExpr)) :::
                                (Push(r0) :: Nil) ::: generateExpression(compOp.rightExpr) ::: (Compare(r0, spReference) :: Nil)
                                mainInstructions ::: (Add(sp, sp, ImmNum(4)) :: Nil)

  }

  def generateBooleanBinaryOperation(boolOp: BooleanBinaryOperationNode): List[Instruction] = {

    boolOp match {

      case boolBin: LogicalAndNode => null
      case boolBin: LogicalOrNode => null
    }
  }


}
