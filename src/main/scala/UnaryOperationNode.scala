trait UnaryOperationNode extends ExprNode {

  val argument: ExprNode 

}

class LogicalNotNode(val argument: ExprNode) extends UnaryOperationNode {

}

class NegativeNode(val argument: ExprNode) extends UnaryOperationNode {

}

class LenNode(val argument: ExprNode) extends UnaryOperationNode {

}

class OrdNode(val argument: ExprNode) extends UnaryOperationNode {

}

class ChrNode(val argument: ExprNode) extends UnaryOperationNode {

}
