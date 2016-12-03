trait Operand {

}

case class StackPointerReference(val offset: Int) extends Operand {

  override def toString() = {
    if (offset > 0) s"[sp + $offset]"
    else {
      if (offset == 0) "[sp]" else s"[sp - ${-offset}]"
    }
  }

  //todo offsett zero

}

case class FramePointerReference(offset: Int) extends Operand {
  override def toString() = {
    if (offset > 0) s"[fp + $offset]"
    else {
      if (offset == 0) "[fp]" else s"[fp - ${-offset}]"
    }
  }
}

case class RegisterStackReference(register: Register, offset: Int = 0) extends Operand {
  // E.g. StackreferenceRegister(r0) Would be [r0] in assembly

  override def toString() = {
    if (offset != 0) s"[${register.toString}, #$offset]"
    else s"[${register.toString}]"
  }

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
