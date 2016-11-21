trait UnaryOperationNode extends ExprNode {

  def argument: ExprNode

}

case class LogicalNotNode(override val argument: ExprNode) extends UnaryOperationNode {
  override def getType: TypeNode = {
    if (argument.getType == BoolTypeNode()) {
      BoolTypeNode()
    } else {
      ErrorLog.add("[Semantic Error]: Argument of bool type expected in logical not operation.")
      ErrorTypeNode()
    }
  }
}

case class NegationNode(override val argument: ExprNode) extends UnaryOperationNode {
  override def getType: TypeNode = {
    if (argument.getType == IntTypeNode()) {
      IntTypeNode()
    } else {
      ErrorLog.add("[Semantic Error]: Argument of int type expected in negation operation.")
      ErrorTypeNode()
    }
  }
}

case class LenNode(override val argument: ExprNode) extends UnaryOperationNode { }

case class OrdNode(override val argument: ExprNode) extends UnaryOperationNode { }

case class ChrNode(override val argument: ExprNode) extends UnaryOperationNode { }
