
import Condition._

trait Branch extends Instruction {

  val goToLabel: String

}

case class StandardBranch(override val goToLabel: String, override val cond: Condition = AL) extends Branch {

  override def toString() = "B" + cond + " " + goToLabel

}

case class BranchLink(override val goToLabel: String, override val cond: Condition = AL) extends Branch {

  override def toString() = "BL" + cond + " " + goToLabel


}
