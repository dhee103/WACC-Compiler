trait ExprNode extends AssignmentRightNode {
  def nodeType: Option[TypeNode] = None
}


case class IntLiteralNode(val value: Int) extends ExprNode {
  override val nodeType = Some(new IntTypeNode)
}

case class BoolLiteralNode(val value: Boolean) extends ExprNode {
  override val nodeType = Some(new BoolTypeNode)
}

case class CharLiteralNode(val value: Char) extends ExprNode {
  override val nodeType = Some(new CharTypeNode)
}

case class StringLiteralNode(val value: String) extends ExprNode {
  override val nodeType = Some(new StringTypeNode)
}

case class PairLiteralNode() extends ExprNode {
  override val nodeType = Some(new PairTypeNode(null, null))
}
