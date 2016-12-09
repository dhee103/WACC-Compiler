trait StatNode extends AstNode {
}

case class BreakNode() extends StatNode {
}

case class SwitchNode(val exprChildren: List[ExprNode], val statChildren: List[StatNode]) extends StatNode with ScopeExtender{
}

case class SkipStatNode() extends StatNode {
}

case class DeclarationNode(val variableType: TypeNode, var identifier: IdentNode, val rhs: AssignmentRightNode) extends StatNode {
}

case class AssignmentNode(var lhs: AssignmentLeftNode, val rhs: AssignmentRightNode) extends StatNode {
}

case class ReadNode(val variable: AssignmentLeftNode) extends StatNode {
}

case class FreeNode(val variable: ExprNode) extends StatNode {
}

case class ReturnNode(val returnValue: ExprNode) extends StatNode {
}

case class ExitNode(val exitCode: ExprNode) extends StatNode {
}

case class PrintNode(val text: ExprNode) extends StatNode {
}

case class PrintlnNode(val text: ExprNode) extends StatNode {
}

//case class IfThenElseNode(condition: ExprNode, thenStat: StatNode, elseStat: StatNode) extends StatNode with ScopeExtender {
//}

case class IfNode(condition: ExprNode, thenStat: StatNode, elifConds: List[ExprNode], elifStats: List[StatNode], elseStat: Option[StatNode] = None) extends StatNode with ScopeExtender {
}

case class WhileNode(condition: ExprNode, loopBody: StatNode) extends StatNode with ScopeExtender {
}

case class NewBeginNode(val body: StatNode) extends StatNode with ScopeExtender {
}

case class SequenceNode(val fstStat: StatNode, val sndStat: StatNode) extends StatNode {
}
