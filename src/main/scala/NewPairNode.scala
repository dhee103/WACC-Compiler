

/**
  * Created by dsg115 on 12/11/16.
  */
class NewPairNode(val _fstElem: ExprNode, val _sndElem: ExprNode) extends AssignmentRightNode {
  val fstElem: ExprNode = _fstElem
  val sndElem: ExprNode = _sndElem
}
