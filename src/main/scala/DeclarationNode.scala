

/**
  * Created by dsg115 on 12/11/16.
  */
class DeclarationNode(val _variableType: TypeNode, val _identifier: IdentNode, val _rhs: AssignmentRightNode) extends StatNode {

  val variableType: TypeNode = _variableType
  var identifier: IdentNode = _identifier
  val rhs: AssignmentRightNode = _rhs

}
