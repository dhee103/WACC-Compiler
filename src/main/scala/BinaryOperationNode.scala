trait BinaryOperationNode extends ExprNode {

  def leftExpr: ExprNode

  def rightExpr: ExprNode

}

trait ArithmeticBinaryOperationNode extends BinaryOperationNode {
  override def getType: TypeNode = {
    val bothExprsInts = leftExpr.getType == IntTypeNode() && rightExpr.getType == IntTypeNode()
    if (bothExprsInts) {
      IntTypeNode()
    } else {
      ErrorLog.add("[Semantic Error]: Argument of int type expected in arithmetic binary operation.")
      ErrorTypeNode()
    }
  }
}

trait OrderComparisonOperationNode extends BinaryOperationNode {
  override def getType: TypeNode = {
    val bothExprsInts = leftExpr.getType == IntTypeNode() && rightExpr.getType == IntTypeNode()
    val bothExprsChars = leftExpr.getType == CharTypeNode() && rightExpr.getType == CharTypeNode()
    if (bothExprsInts || bothExprsChars) {
      BoolTypeNode()
    } else {
      ErrorLog.add("[Semantic Error]: Argument of wrong type in arithmetic binary operation.")
      ErrorTypeNode()
    }
  }
}

trait ComparisonOperationNode extends BinaryOperationNode {
  override def getType: TypeNode = {
    val exprsAreSameType = leftExpr.getType == rightExpr.getType
    if (exprsAreSameType) {
      BoolTypeNode()
    } else {
      ErrorLog.add("[Semantic Error]: Argument of non-matching types in comparison operation.")
      ErrorTypeNode()
    }
  }
}


trait BooleanBinaryOperationNode extends BinaryOperationNode {
  override def getType: TypeNode = {
    val bothExprsBools = leftExpr.getType == BoolTypeNode() && rightExpr.getType == BoolTypeNode()
    if (bothExprsBools) {
      BoolTypeNode()
    } else {
      ErrorLog.add("[Semantic Error]: Argument of boolean type expected in logical binary operation.")
      ErrorTypeNode()
    }
  }
}

case class MulNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends ArithmeticBinaryOperationNode {}

case class DivNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends ArithmeticBinaryOperationNode {}

case class ModNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends ArithmeticBinaryOperationNode {}

case class PlusNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends ArithmeticBinaryOperationNode {}

case class MinusNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends ArithmeticBinaryOperationNode {}

case class GreaterThanNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends OrderComparisonOperationNode {}

case class GreaterEqualNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends OrderComparisonOperationNode {}

case class LessThanNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends OrderComparisonOperationNode {}

case class LessEqualNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends OrderComparisonOperationNode {}

case class DoubleEqualNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends ComparisonOperationNode {}

case class NotEqualNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends ComparisonOperationNode {}

case class LogicalAndNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BooleanBinaryOperationNode {}

case class LogicalOrNode(override val leftExpr: ExprNode, override val rightExpr: ExprNode)
  extends BooleanBinaryOperationNode { }


