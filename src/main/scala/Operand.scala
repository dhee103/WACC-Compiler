trait Operand {

}

case class StackReference(val offset: Int) extends Operand {

  override def toString() = "[sp, #" + offset + "]"

}

case class DataCall(val label: String) extends Operand {

  override def toString() = label

}

case class ImmNum(value: Int) extends Operand {

  override def toString() = "#" + value

  //todo =num for load??
}
