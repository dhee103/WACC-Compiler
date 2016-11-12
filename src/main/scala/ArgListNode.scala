

/**
  * Created by dsg115 on 12/11/16.
  */
class ArgListNode(val _exprChildren: IndexedSeq[ExprNode]) extends AstNode {

  val exprChildren: Array[ExprNode] = _exprChildren.toArray

}
