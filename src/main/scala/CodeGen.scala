object CodeGen {

  val lr: LinkRegister = LinkRegister()
  val r0: ResultRegister = ResultRegister()
  val pc: ProgramCounter = ProgramCounter()
  val zero: ImmNum = ImmNum(0)
  val pushlr = Push(lr)
  val poppc = Pop(pc)


  def generateProgramCode(prog: ProgNode): List[Instruction] = {

    val statement: StatNode = prog.statChild


    (Directive(".text")) :: (Directive(".global main")) ::
    (Label("main")) :: pushlr :: generateStatement(statement) ++ (Move(r0, zero) ::
    poppc :: Nil)

  }

  def generateStatement(statement: StatNode): List[Instruction] = {

    statement match {

      case stat: DeclarationNode => generateDeclaration(stat)
      case stat: AssignmentNode  => generateAssignment(stat)
      case stat: SkipStatNode    => Nil

    }
  }

  def generateDeclaration(decl: DeclarationNode): List[Instruction] = {
    null
  }

  def generateAssignment(assignment: AssignmentNode): List[Instruction] = {
    null
  }
}
