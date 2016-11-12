

/**
  * Created by dsg115 on 12/11/16.
  */
class WhileNode(val _condition: ExprNode, val _loopBody: StatNode) extends StatNode {

  val condition: ExprNode = _condition
  val loopBody: StatNode = _loopBody

}
