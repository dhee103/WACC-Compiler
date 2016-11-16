trait ExprNode extends AssignmentRightNode {
  var nodeType: Option[TypeNode] = None
}


case class IntLiteralNode(val value: Int) extends ExprNode {
  nodeType = Some(new IntTypeNode)
}

case class BoolLiteralNode(val value: Boolean) extends ExprNode {
  nodeType = Some(new BoolTypeNode)
}

case class CharLiteralNode(val value: Char) extends ExprNode {
   nodeType = Some(new CharTypeNode)
}

case class StringLiteralNode(val value: String) extends ExprNode {
   nodeType = Some(new StringTypeNode)
}

case class PairLiteralNode() extends ExprNode {
   nodeType = Some(new PairTypeNode(null, null))
}
