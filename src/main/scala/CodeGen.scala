object CodeGen {

  val lr: LinkRegister = LinkRegister()
  val r0: ResultRegister = ResultRegister()
  val pc: ProgramCounter = ProgramCounter()
  val zero: ImmNum = ImmNum(0)
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

  def generateExpression(expr: ExprNode): List[Instruction] = {

    expr match {
      case expr: IdentNode            => Move(r0, StackReference(stack.getOffsetForIdentifier(expr))) :: Nil
      case expr: ArrayElemNode        => null
      case expr: UnaryOperationNode   => null
      case expr: BinaryOperationNode  => null
      case expr: IntLiteralNode       => null
      case expr: BoolLiteralNode      => null
      case expr: CharLiteralNode      => null
      case expr: StringLiteralNode    => null
      case expr: PairLiteralNode      => null
      case _                          => null
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

    List[Instruction](Load(r0, exitCode), Jump("exit"))
  }
}
