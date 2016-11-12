

/**
  * Created by dsg115 on 12/11/16.
  */
class CallNode(val _id: IdentNode, val _argList: Option[ArgListNode]) extends AssignmentRightNode {
  val id: IdentNode = _id
  val argList: Option[ArgListNode] = _argList
}
