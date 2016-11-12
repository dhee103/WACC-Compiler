

/**
  * Created by dsg115 on 12/11/16.
  */
class IfNode(val _condition: ExprNode, val _thenStat: StatNode, val _elseStat: StatNode) extends StatNode {

  val condition: ExprNode = _condition
  val thenStat: StatNode = _thenStat
  val elseStat: StatNode = _elseStat

}
