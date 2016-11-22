trait Register extends Operand{

}

case class StackPointer() extends Register {

  override def toString() = "sp"

}

case class BasePointer() extends Register {

  override def toString() = "R11"

}

case class LinkRegister() extends Register {

  override def toString() = "lr"

}

case class ProgramCounter() extends Register {

  override def toString() = "pc"

}

case class ResultRegister() extends Register {

  override def toString() = "R0"

}
