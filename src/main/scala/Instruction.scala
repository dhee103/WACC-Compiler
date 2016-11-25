import Condition._

trait Instruction {

  val label: Option[String] = None
  val cond: Condition = AL

}

case class Push(src: Register, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  def this(src: Register, label: Option[String]) = this(src, AL, label)
  def this(src: Register, cond: Condition) = this(src, cond, None)
  override def toString = label.getOrElse("") + "PUSH" + cond + " {" + src.toString + "}"

}

case class Pop(dst: Register, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  def this(dst: Register, label: Option[String]) = this(dst, AL, label)
  def this(dst: Register, cond: Condition) = this(dst, cond, None)
  override def toString = "POP" + cond + " {" + dst.toString + "}"

}

case class Add(dst: Register, src1: Register, src2: Operand, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  def this(dst: Register, src1: Register, src2: Operand, label: Option[String]) = this(dst, src1, src2, AL, label)
  def this(dst: Register, src1: Register, src2: Operand, cond: Condition) = this(dst, src1, src2, cond, None)
  override def toString = "ADD" + cond + " " + dst.toString + ", " + src1.toString + ", " + src2.toString

}

case class Sub(dst: Register, src1: Register, src2: Operand, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  def this(dst: Register, src1: Register, src2: Operand, label: Option[String]) = this(dst, src1, src2, AL, label)
  def this(dst: Register, src1: Register, src2: Operand, cond: Condition) = this(dst, src1, src2, cond, None)
  override def toString = "SUB" + cond + " " + dst.toString + ", " + src1.toString + ", " + src2.toString

}

case class ReverseSubNoCarry(dst: Register, src1: Register, src2: Operand, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  def this(dst: Register, src1: Register, src2: Operand, label: Option[String]) = this(dst, src1, src2, AL, label)
  def this(dst: Register, src1: Register, src2: Operand, cond: Condition) = this(dst, src1, src2, cond, None)
  override def toString = "RSB" + cond + " " + dst.toString + ", " + src1.toString + ", " + src2.toString

}

case class Load(dst: Register, src: Operand, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  def this(dst: Register, src: Operand, label: Option[String]) = this(dst, src, AL, label)
  def this(dst: Register, src: Operand, cond: Condition) = this(dst, src, cond, None)
  override def toString = "LDR" + cond + " " + dst.toString + ", " + src.toString

}


case class Store(src: Register, dst: Operand, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  def this(src: Register, dst: Operand, label: Option[String]) = this(src, dst, AL, label)
  def this(src: Register, dst: Operand, cond: Condition) = this(src, dst, cond, None)
  override def toString() = "STR" + cond + " " + src.toString() + ", " + dst.toString()

}

case class Move(dst: Register, src: Operand, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  def this(dst: Register, src: Operand, label: Option[String]) = this(dst, src, AL, label)
  def this(dst: Register, src: Operand, cond: Condition) = this(dst, src, cond, None)
  override def toString = "MOV" + cond + " " + dst.toString + ", " + src.toString

}

case class SMull(dst1: Register, dst2: Register, src1: Register, src2: Register, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  def this(dst1: Register, dst2: Register, src1: Register, src2: Register, label: Option[String]) = this(dst1, dst2, src1, src2, AL, label)
  def this(dst1: Register, dst2: Register, src1: Register, src2: Register, cond: Condition) = this(dst1, dst2, src1, src2, cond, None)
  override def toString = "SMULL" + cond + " " + dst1.toString + ", " + dst2.toString + ", " + src1.toString + ", " + src2.toString

}

case class SDiv(dst: Register, src1: Register, src2: Register, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  def this(dst: Register, src1: Register, src2: Register, label: Option[String]) = this(dst, src1, src2, AL, label)
  def this(dst: Register, src1: Register, src2: Register, cond: Condition) = this(dst, src1, src2, cond, None)
  override def toString = "SDIV" + cond + " " + dst.toString + ", " + src1.toString+ ", " + src2.toString

}

case class Compare(cmp1: Register, cmp2: Operand, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  def this(cmp1: Register, cmp2: Operand, label: Option[String]) = this(cmp1, cmp2, AL, label)
  def this(cmp1: Register, cmp2: Operand, cond: Condition) = this(cmp1, cmp2, cond, None)
  override def toString= "CMP" + cond + " " + cmp1.toString + ", " + cmp2.toString

}

case class Directive(name: String, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  override def toString = "." + name
}

case class Label(name: String, override val cond: Condition = AL, override val label: Option[String] = None) extends Instruction {
  override def toString = name + ":"
}

case class And(val dst: Register, val src1: Register, val src2: Operand, override val cond: Condition = AL) extends Instruction {

  override def toString() = "AND" + cond + " " + dst.toString() + ", " + src1.toString() + ", " + src2.toString()


}

case class Orr(val dst: Register, val src1: Register, val src2: Operand, override val cond: Condition = AL) extends Instruction {

  override def toString() = "ORR" + cond + " " + dst.toString() + ", " + src1.toString() + ", " + src2.toString()


}


class Function(val label: String, var body: List[Instruction]) {

  val initialPush: Push = new Push(new StackPointer)

  val finalPop: Pop = new Pop(new ProgramCounter())

  //todo

}
