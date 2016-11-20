trait ExprNode extends AssignmentRightNode {
}


case class IntLiteralNode(val value: Int) extends ExprNode {
  override def getType = new IntTypeNode
}

case class BoolLiteralNode(val value: Boolean) extends ExprNode {
  override def getType = new BoolTypeNode
}

case class CharLiteralNode(val value: Char) extends ExprNode {
   override def getType = new CharTypeNode
}

case class StringLiteralNode(val value: String) extends ExprNode {
   override def getType = new StringTypeNode
}

case class PairLiteralNode() extends ExprNode {
   override def getType = new PairTypeNode(null, null)
}
