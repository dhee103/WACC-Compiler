

/**
  * Created by dsg115 on 12/11/16.
  */
class ArrayElemNode(val _identifier: IdentNode, val _indices: IndexedSeq[ExprNode]) extends ExprNode with  AssignmentLeftNode {

  val identifier: IdentNode = _identifier
  val indices: Array[ExprNode] = _indices.toArray

}
