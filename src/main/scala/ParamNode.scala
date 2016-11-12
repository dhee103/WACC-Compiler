

/**
  * Created by dsg115 on 12/11/16.
  */
class ParamNode(val _variableType: TypeNode, val _identifier: IdentNode) extends
  AstNode {
  val variableType = _variableType
  val identifier = _identifier
}
