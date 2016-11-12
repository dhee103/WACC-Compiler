

/**
  * Created by dsg115 on 12/11/16.
  */
class ParamListNode(val _params: IndexedSeq[ParamNode]) extends AstNode {
  val params: Array[ParamNode] = _params.toArray
}
