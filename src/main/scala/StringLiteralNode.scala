

/**
  * Created by dsg115 on 12/11/16.
  */
class StringLiteralNode(val _value: String) extends ExprNode {

  val value: String = _value
  nodeType = new StringTypeNode


}
