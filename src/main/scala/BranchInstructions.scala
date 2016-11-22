case class Jump(val goToLabel: String) extends Instruction {

  override def toString() = "BL " + goToLabel

}

case class BranchEqual() extends Instruction {
  //todo

}
