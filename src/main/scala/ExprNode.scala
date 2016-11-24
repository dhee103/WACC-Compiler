trait ExprNode extends AssignmentRightNode {
}


case class IntLiteralNode(val value: Int) extends ExprNode {
  override def getType = IntTypeNode()
}

case class BoolLiteralNode(val value: Boolean) extends ExprNode {
  override def getType = BoolTypeNode()
}

case class CharLiteralNode(val value: Char) extends ExprNode {
   override def getType = CharTypeNode()
}

case class StringLiteralNode(val value: String) extends ExprNode {
   override def getType = StringTypeNode()
}

case class PairLiteralNode() extends ExprNode {
   override def getType = PairTypeNode(AnyTypeNode(), AnyTypeNode())
}
