trait AssignmentRightNode extends AstNode {

}

class NewPairNode(val _fstElem: ExprNode, val _sndElem: ExprNode) extends AssignmentRightNode {
  val fstElem: ExprNode = _fstElem
  val sndElem: ExprNode = _sndElem
}

class CallNode(val _id: IdentNode, val _argList: ArgListNode) extends AssignmentRightNode {
  val id: IdentNode = _id
  val argList: Option[ArgListNode] = Some(_argList)
}
