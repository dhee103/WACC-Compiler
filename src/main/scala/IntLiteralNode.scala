

/**
  * Created by dsg115 on 12/11/16.
  */
class IntLiteralNode(val _value: Int) extends ExprNode {

  val value: Int = _value
  nodeType = new IntTypeNode

}
