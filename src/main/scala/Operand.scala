trait Operand {

}

case class StackReference(val offset: Int) extends Operand {

}

case class FunctionCall(val label: String) extends Operand {

}
