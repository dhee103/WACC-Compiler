trait Register extends Operand{

}

case class StackPointer() extends Register {

  override def toString() = "sp"

}

// TODO: Change to frame pointer
case class BasePointer() extends Register {

  override def toString() = "r11"

}

case class LinkRegister() extends Register {

  override def toString() = "lr"

}

case class ProgramCounter() extends Register {

  override def toString() = "pc"

}

case class ResultRegister() extends Register {

  override def toString() = "r0"

}

case class R1() extends Register {

  override def toString() = "r1"
}
