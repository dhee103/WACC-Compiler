trait AstNode {
}

case class ProgNode(val statChild: StatNode, val funcChildren: IndexedSeq[FuncNode]) extends AstNode {
}

// TODO: Change typeSignature to returnType
case class FuncNode(val typeSignature: TypeNode, val identifier: IdentNode,
                    val paramList: ParamListNode, val statement: StatNode)
  extends AstNode {
}

case class ParamListNode(val params: IndexedSeq[ParamNode]) extends AstNode {
}

case class ParamNode(val variableType: TypeNode, val identifier: IdentNode) extends
  AstNode {
}

trait AssignmentLeftNode extends AstNode {
}

case class ArgListNode(val exprs: IndexedSeq[ExprNode]) extends AstNode {
}

trait PairElemNode extends AssignmentLeftNode with AssignmentRightNode {

  def exprChild: ExprNode

  def getType: TypeNode = exprChild.getType // this is wrong?

}

case class FstNode(override val exprChild: ExprNode) extends PairElemNode {
}

case class SndNode(override val exprChild: ExprNode) extends PairElemNode {
}

case class IdentNode(val name: String) extends ExprNode with AssignmentLeftNode {
  var identType: Option[TypeNode] = None

  override def getType: TypeNode = identType.getOrElse(throw new RuntimeException("Fatal Error: Identifier not annotated."))
}

case class ArrayElemNode(val identifier: IdentNode, val exprs: IndexedSeq[ExprNode]) extends ExprNode with AssignmentLeftNode {
  override def getType: TypeNode = {
    identifier.getType match {
      case ArrayTypeNode(elemType) => elemType
      case _ => throw new RuntimeException("Semantic Error: ")
    }
  }
}

case class ArrayLiteralNode(val values: IndexedSeq[ExprNode]) extends AssignmentRightNode {
  // Also need to check that each ExprNode here has a nodeType of IntTypeNode
  override def getType: TypeNode = {
    if (values.isEmpty) {
      ArrayTypeNode(null)
    } else {
      val elemType: TypeNode = values(0).getType
      ArrayTypeNode(elemType)
    }
  }
}
