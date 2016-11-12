

/**
  * Created by dsg115 on 12/11/16.
  */
class CharLiteralNode(val _value: Char) extends ExprNode {

  val value: Char = _value
  nodeType = new CharTypeNode


}
