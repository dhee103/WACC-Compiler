
trait Branch extends Instruction {

  val goToLabel: String

}

case class Jump(override val goToLabel: String) extends Branch {

  override def toString() = "BL " + goToLabel

}

case class BranchEqual(override val goToLabel: String) extends Branch {

  override def toString() = "BEQ " + goToLabel


}

case class NotEqual(override val goToLabel: String) extends Branch {

  override def toString() = "NE " + goToLabel
}

case class GreaterEqual(override val goToLabel: String) extends Branch {

  override def toString() = "BGE " + goToLabel
}

case class LessEqual(override val goToLabel: String) extends Branch {

  override def toString() = "BLE " + goToLabel
}



//branches with a condition
