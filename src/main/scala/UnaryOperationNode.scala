trait UnaryOperationNode extends ExprNode {

  def argument: ExprNode

}

case class LogicalNotNode(override val argument: ExprNode) extends UnaryOperationNode {
  override def getType: TypeNode = {
    if (argument.getType == BoolTypeNode()) {
      BoolTypeNode()
    } else {
      SemanticErrorLog.add("Argument of bool type expected in logical not operation.")
      ErrorTypeNode()
    }
  }
}

case class NegationNode(override val argument: ExprNode) extends UnaryOperationNode {
  override def getType: TypeNode = {
    if (argument.getType == IntTypeNode()) {
      IntTypeNode()
    } else {
      SemanticErrorLog.add("Argument of int type expected in negation operation.")
      ErrorTypeNode()
    }
  }
}

case class LenNode(override val argument: ExprNode) extends UnaryOperationNode {
  override def getType: TypeNode = {
    if (argument.getType.isInstanceOf[ArrayTypeNode]) {
      IntTypeNode()
    } else {
      SemanticErrorLog.add("Array expected as argument of len operation.")
      ErrorTypeNode()
    }
  }
}

case class OrdNode(override val argument: ExprNode) extends UnaryOperationNode {
  override def getType: TypeNode = {
    if (argument.getType == CharTypeNode()) {
      IntTypeNode()
    } else {
      SemanticErrorLog.add("Argument of char type expected in ord operation.")
      ErrorTypeNode()
    }
  }
}

case class ChrNode(override val argument: ExprNode) extends UnaryOperationNode {
  override def getType: TypeNode = {
    if (argument.getType == IntTypeNode()) {
      CharTypeNode()
    } else {
      SemanticErrorLog.add("Argument of int type expected in chr operation.")
      ErrorTypeNode()
    }
  }
}
