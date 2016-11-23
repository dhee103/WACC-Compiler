object CodeGen {

  val lr: LinkRegister = new LinkRegister()
  val r0: ResultRegister = new ResultRegister()
  val pc: ProgramCounter = new ProgramCounter()
  val zero: ImmNum = new ImmNum(0)
  val pushlr = new Push(lr)
  val poppc = new Pop(pc)


  def generateProgramCode(prog: ProgNode): List[Instruction] = {

    val statement: StatNode = prog.statChild


    (new Directive(".text")) :: (new Directive(".global main")) ::
    (new Label("main")) :: pushlr :: generateStatement(statement) ++ (new Move(r0, zero) ::
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
