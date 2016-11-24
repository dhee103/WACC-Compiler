
import Condition._

trait Branch extends Instruction {

  val goToLabel: String

  override def toString() = cond + goToLabel

}

case class StandardBranch(override val goToLabel: String, override val cond: Condition = AL) extends Branch {

}

case class BranchLink(override val goToLabel: String, override val cond: Condition = AL) extends Branch {

}
