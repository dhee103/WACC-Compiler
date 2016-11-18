trait AssignmentRightNode extends AstNode {
  var nodeType: Option[TypeNode]
}

case class NewPairNode(val fstElem: ExprNode, val sndElem: ExprNode) extends AssignmentRightNode {
}

case class CallNode(val id: IdentNode, val argList: Option[ArgListNode]) extends AssignmentRightNode {
}
