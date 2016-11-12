

/**
  * Created by dsg115 on 12/11/16.
  */
class BoolLiteralNode(val _value: Boolean) extends ExprNode {

  val value: Boolean = _value
  nodeType = new BoolTypeNode


}
