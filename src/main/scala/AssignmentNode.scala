

/**
  * Created by dsg115 on 12/11/16.
  */
class AssignmentNode(val _lhs: AssignmentLeftNode, val _rhs: AssignmentRightNode) extends StatNode {

  var lhs: AssignmentLeftNode = _lhs
  var rhs: AssignmentRightNode = _rhs

}
