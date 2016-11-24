import Condition._

object CodeGen {

  val lr = LinkRegister()
  val r0  = ResultRegister()
  val pc  = ProgramCounter()
  val zero = ImmNum(0)
  val loadZero = LoadImmNum(0)
  val pushlr = Push(lr)
  val poppc = Pop(pc)
  val ltorg = Ltorg()

  var stack = new AssemblyStack()

  def generateProgramCode(prog: ProgNode): List[Instruction] = {

    val statement: StatNode = prog.statChild


    (Directive(".text") :: Directive("\n.global main") ::
    (Label("main")) :: pushlr :: generateStatement(statement)) ::: (Move(r0, zero) ::
    poppc :: ltorg ::  Nil)

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
      case IntLiteralNode(value) => ImmNum(value)
      case _ => throw new UnsupportedOperationException("anything that could go to an int")
    }

    List[Instruction](Load(r0, exitCode), BranchLink("exit"))
  }

  def generateExpression(expr: ExprNode): List[Instruction] = {

    expr match {
      case expr: IdentNode            => Move(r0, StackReference(stack.getOffsetForIdentifier(expr))) :: Nil
      case expr: ArrayElemNode        => null
      case expr: UnaryOperationNode   => generateUnaryOperation(expr)
      case expr: BinaryOperationNode  => null
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
      case unOp : LenNode           =>
      case unOp : OrdNode           =>
      case unOp : ChrNode           =>

    }

    null

  }


}
