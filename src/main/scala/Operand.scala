trait Operand {

}

case class StackReference(val offset: Int) extends Operand {

  override def toString() = if (offset == 0) "[sp]" else ("[sp, #" + offset + "]")

  //todo offsett zero

}

case class StackReferenceRegister(register: Register) extends Operand {
  // E.g. StackreferenceRegister(r0) Would be [r0] in assembly

  override def toString() = s"[${register.toString}]"

  //todo offsett zero

}



case class DataCall(val label: String) extends Operand {

  override def toString() = label

}

case class ImmNum(value: Int) extends Operand {

  override def toString() = "#" + value

  //todo =num for load??
}

case class LoadImmNum(value: Int) extends Operand {

  override def toString() = "=" + value

  //todo =num for load??
}

case class ImmNumChar(value: Char) extends Operand {

  override def toString() = "+" + value

}

case class LabelOp(value: String) extends Operand {

  override def toString() = "=" + value

}
