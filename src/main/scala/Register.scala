trait Register extends Operand{

}

case class StackPointer() extends Register {

  override def toString() = "sp"

}

case class BasePointer() extends Register {

  override def toString() = "R11"

}

case class LinkRegister() extends Register {

}

case class ProgramCounter() extends Register {

}

case class ResultRegister() extends Register {

}
