trait BinaryOperationNode extends ExprNode {

  val leftExpr: ExprNode
  val rightExpr: ExprNode

}

class MulOperationNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class DivOperationNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class ModNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class PlusNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class MinusNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class GreaterThanNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class GreaterEqualNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class LessThanNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class LessEqualNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class DoubleEqualNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class NotEqualNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class LogicalAndNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}

class LogicalOrNode(val leftExpr: ExprNode, val rightExpr: ExprNode) extends BinaryOperationNode {

}
