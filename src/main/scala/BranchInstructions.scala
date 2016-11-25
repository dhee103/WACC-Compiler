
import Condition._

trait Branch extends Instruction {
  val goToLabel: String
}

case class StandardBranch(override val goToLabel: String, override val cond: Condition = AL, override val label: Option[String] = None) extends Branch {
  def this(goToLabel: String, cond: Condition) = this(goToLabel, cond, None)
  def this(goToLabel: String, label: Option[String]) = this(goToLabel, AL, label)
  override val main = "B" + cond + " " + goToLabel
}

case class BranchLink(override val goToLabel: String, override val cond: Condition = AL, override val label: Option[String] = None) extends Branch {
  def this(goToLabel: String, cond: Condition) = this(goToLabel, cond, None)
  def this(goToLabel: String, label: Option[String]) = this(goToLabel, AL, label)
  override val main = "BL" + cond + " " + goToLabel
}
