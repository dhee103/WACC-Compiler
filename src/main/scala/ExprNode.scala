trait ExprNode extends AssignmentRightNode {
}


case class IntLiteralNode(val value: Int) extends ExprNode {
  var nodeType = Some(new IntTypeNode)
}

case class BoolLiteralNode(val value: Boolean) extends ExprNode {
  var nodeType = Some(new BoolTypeNode)
}

case class CharLiteralNode(val value: Char) extends ExprNode {
   var nodeType = Some(new CharTypeNode)
}

case class StringLiteralNode(val value: String) extends ExprNode {
   var nodeType = Some(new StringTypeNode)
}

case class PairLiteralNode() extends ExprNode {
   var nodeType = Some(new PairTypeNode(null, null))
}
