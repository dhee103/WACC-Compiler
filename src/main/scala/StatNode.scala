trait StatNode extends AstNode {
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

case class IfNode (val condition: ExprNode, val thenStat: StatNode, val elseStat: StatNode) extends StatNode {
}

case class WhileNode(val condition: ExprNode, val loopBody: StatNode) extends StatNode {
}

case class NewBeginNode(val body: StatNode) extends StatNode {
}

case class SequenceNode(val fstStat: StatNode, val sndStat: StatNode) extends StatNode {
}
