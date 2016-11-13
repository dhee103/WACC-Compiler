trait UnaryOperationNode extends ExprNode {

  def argument: ExprNode

}

case class LogicalNotNode(override val argument: ExprNode) extends UnaryOperationNode { }

case class NegativeNode(override val argument: ExprNode) extends UnaryOperationNode { }

case class LenNode(override val argument: ExprNode) extends UnaryOperationNode { }

case class OrdNode(override val argument: ExprNode) extends UnaryOperationNode { }

case class ChrNode(override val argument: ExprNode) extends UnaryOperationNode { }
