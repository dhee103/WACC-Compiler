

/**
  * Created by dsg115 on 12/11/16.
  */
class ArrayLiteralNode(val _values: IndexedSeq[ExprNode]) extends AssignmentRightNode {

  val values: Array[ExprNode] = _values.toArray

}
