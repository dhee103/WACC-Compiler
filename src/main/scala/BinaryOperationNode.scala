trait BinaryOperationNode extends ExprNode {

  val firstArg: ExprNode
  val secondArg: ExprNode

}

class MulOperationNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class DivOperationNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class ModNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class PlusNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class MinusNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class GreaterThanNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class GreaterEqualNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class LessThanNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class LessEqualNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class DoubleEqualNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class NotEqualNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class LogicalAndNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}

class LogicalOrNode(val firstArg: ExprNode, val secondArg: ExprNode) extends BinaryOperationNode {

}
