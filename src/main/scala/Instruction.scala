trait Instruction {

}

case class Push(val src: Register) extends Instruction {

  override def toString() = "PUSH " + src.toString()

}

case class Pop(val dst: Register) extends Instruction {

  override def toString() = "POP " + dst.toString()

}

case class Add(val dst: Register, val src1: Register, val src2: Operand) extends Instruction {

  override def toString() = "ADD " + dst.toString() + ", " + src1.toString() + ", " + src2.toString()

}

case class Sub(val dst: Register, val src1: Register, val src2: Operand) extends Instruction {

  override def toString() = "SUB " + dst.toString() + ", " + src1.toString() + ", " + src2.toString()


}

case class Load(val dst: Register, val src: Operand) extends Instruction {

  override def toString() = "LDR " + dst.toString() + ", " + src.toString()

}

case class Store(val src: Register, val dst: Operand) extends Instruction {

  override def toString() = "STR " + src.toString() + ", " + dst.toString()

}

case class Move(val dst: Register, val src: Operand) extends Instruction {

  override def toString() = "MOV " + src.toString() + ", " + dst.toString()

  //todo do we need movlt and movge

}

case class Mul(val dst: Register, val src1: Register, val src2: Register) extends Instruction {

  override def toString() = "MUL " + dst.toString() + ", " + src1.toString() + ", " + src2.toString()

}

case class Compare(val cmp1: Register, val cmp2: Operand) extends Instruction {

  override def toString() = "CMP " + cmp1.toString() + ", " + cmp2.toString()

}


class Function(val label: String, var body: List[Instruction]) {

  val initialPush: Push = new Push(new StackPointer)

  val finalPop: Pop = new Pop(new ProgramCounter())

  //todo

}
