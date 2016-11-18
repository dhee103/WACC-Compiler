trait AssignmentRightNode extends AstNode {
  def nodeType: TypeNode
}

case class NewPairNode(val fstElem: ExprNode, val sndElem: ExprNode) extends AssignmentRightNode {
  override def nodeType: TypeNode = {
    val fstElemType = fstElem.getType()
    val sndElemType = sndElem.getType()
    new PairTypeNode(fstElemType, sndElemType)
  }
}

case class CallNode(val id: IdentNode, val argList: Option[ArgListNode]) extends AssignmentRightNode {
  override def nodeType: TypeNode = FunctionTable.getReturnType(id)
}
