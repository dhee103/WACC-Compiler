trait BinaryOperationNode extends ExprNode {

  def leftExpr: ExprNode
  def rightExpr: ExprNode

}

case class MulNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class DivNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class ModNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class PlusNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class MinusNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class GreaterThanNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class GreaterEqualNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class LessThanNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class LessEqualNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class DoubleEqualNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class NotEqualNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class LogicalAndNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }

case class LogicalOrNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BinaryOperationNode { }
