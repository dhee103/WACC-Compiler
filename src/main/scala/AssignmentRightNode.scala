trait AssignmentRightNode extends AstNode {
  def getType: TypeNode
}

case class NewPairNode(val fstElem: ExprNode, val sndElem: ExprNode) extends AssignmentRightNode {
  override def getType: TypeNode = {
    val fstElemType = fstElem.getType
    val sndElemType = sndElem.getType
    // TO DO: Write our own function to get PairElemTypeNode from TypeNode
    new PairTypeNode(fstElemType.toPairElemTypeNode, sndElemType.toPairElemTypeNode)
  }
}

case class CallNode(val id: IdentNode, val argList: Option[ArgListNode]) extends AssignmentRightNode {
  override def getType: TypeNode = FunctionTable.getReturnType(id)
}
