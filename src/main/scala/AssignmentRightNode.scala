trait AssignmentRightNode extends AstNode {

}

class NewPairNode extends AssignmentRightNode {
  val fstElem: ExprNode
  val sndElem: ExprNode
}

class CallNode extends AssignmentRightNode {
  val id: IdentNode
  val argList: Option[ArgListNode]
}
