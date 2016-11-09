trait ExprNode extends AssignmentRightNode {

}

class IntLiteralNode(val _value: Int) extends ExprNode {

  val value: Int = _value

}

class BoolLiteralNode(val _value: Boolean) extends ExprNode {

  val value: Boolean = _value

}

class CharLiteralNode(val _value: Char) extends ExprNode {

  val value: Char = _value

}

class StringLiteralNode(val _value: String) extends ExprNode {

  val value: String = _value

}

class PairLiteralNode extends ExprNode {


}
