

/**
  * Created by dsg115 on 12/11/16.
  */
class ProgNode(val _statChild: StatNode, val _funcChildren: IndexedSeq[FuncNode]) extends AstNode {

  val funcChildren: Array[FuncNode] = _funcChildren.toArray
  val statChild: StatNode = _statChild

}
