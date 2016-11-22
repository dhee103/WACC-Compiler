case class Jump(val goToLabel: String) extends Instruction {

  override def toString() = "BL " + goToLabel

}

case class BranchEqual(val goToLabel: String) extends Instruction {

  override def toString() = "BEQ" + goToLabel


}
