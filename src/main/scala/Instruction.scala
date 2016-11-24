import Condition._

trait Instruction {

  val cond: Condition = AL

  def prefix: String = if(cond == AL) " " else cond + " "

}

case class Push(val src: Register, override val cond: Condition = AL) extends Instruction {

  override def toString() = "PUSH" + prefix + "{" + src.toString() + "}"

}

case class Pop(val dst: Register, override val cond: Condition = AL) extends Instruction {

  override def toString() = "POP" + prefix + "{" + dst.toString() + "}"

}

case class Add(val dst: Register, val src1: Register, val src2: Operand, override val cond: Condition = AL) extends Instruction {

  override def toString() = "ADD" + prefix + dst.toString() + ", " + src1.toString() + ", " + src2.toString()

}

case class Sub(val dst: Register, val src1: Register, val src2: Operand, override val cond: Condition = AL) extends Instruction {

  override def toString() = "SUB" + prefix + dst.toString() + ", " + src1.toString() + ", " + src2.toString()


}

case class Load(val dst: Register, val src: Operand, override val cond: Condition = AL) extends Instruction {

  override def toString() = "LDR" + prefix + dst.toString() + ", " + src.toString()

}


case class Store(val src: Register, val dst: Operand, override val cond: Condition = AL) extends Instruction {

  override def toString() = "STR" + prefix + src.toString() + ", " + dst.toString()

}

case class Move(val dst: Register, val src: Operand, override val cond: Condition = AL) extends Instruction {

  override def toString() = "MOV" + prefix + dst.toString() + ", " + src.toString()

  //todo do we need movlt and movge

}

case class SMull(val dst1: Register, dst2: Register, val src1: Register, val src2: Register, override val cond: Condition = AL) extends Instruction {

  override def toString() = "SMULL" + prefix + dst1.toString() + ", " + dst2.toString() + ", " + src1.toString() + ", " + src2.toString()

}

case class Compare(val cmp1: Register, val cmp2: Operand, override val cond: Condition = AL) extends Instruction {

  override def toString() = "CMP" + prefix + cmp1.toString() + ", " + cmp2.toString()

}

case class Directive(val name: String) extends Instruction {

  override def toString() = name

}

case class Label(val name: String) extends Instruction {

  override def toString() = name + ":"

}


class Function(val label: String, var body: List[Instruction]) {

  val initialPush: Push = new Push(new StackPointer)

  val finalPop: Pop = new Pop(new ProgramCounter())

  //todo

}
