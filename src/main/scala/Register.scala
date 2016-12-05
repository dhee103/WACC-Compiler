trait Register extends Operand{

}

case class StackPointer() extends Register {

  override def toString() = "sp"

}

case class FramePointer() extends Register {

  override def toString() = "fp"

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

case class R2() extends Register {

  override def toString() = "r2"
}

case class R3() extends Register {

  override def toString() = "r3"
}

case class R4() extends Register {

  override def toString() = "r4"
}
