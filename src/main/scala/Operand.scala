trait Operand {

}

case class StackReference(val offset: Int) extends Operand {

  override def toString() = "[sp, #" + offset + "]"

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
