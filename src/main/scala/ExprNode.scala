trait ExprNode extends AssignmentRightNode {

var nodeType: TypeNode = null

}

class IntLiteralNode(val _value: Int) extends ExprNode {

  val value: Int = _value
  nodeType = new IntTypeNode

}

class BoolLiteralNode(val _value: Boolean) extends ExprNode {

  val value: Boolean = _value
  nodeType = new BoolTypeNode


}

class CharLiteralNode(val _value: Char) extends ExprNode {

  val value: Char = _value
  nodeType = new CharTypeNode


}

class StringLiteralNode(val _value: String) extends ExprNode {

  val value: String = _value
  nodeType = new StringTypeNode


}

class PairLiteralNode extends ExprNode {

  nodeType = new PairTypeNode(null, null)


}
