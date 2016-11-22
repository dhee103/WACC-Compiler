trait AstNode {
}

case class ProgNode(val statChild: StatNode, val funcChildren: IndexedSeq[FuncNode]) extends AstNode {
}

case class FuncNode(val returnType: TypeNode, val identifier: IdentNode,
                    val paramList: ParamListNode, val statement: StatNode)
  extends AstNode {
}

case class ParamListNode(val params: IndexedSeq[ParamNode]) extends AstNode {
}

case class ParamNode(val variableType: TypeNode, val identifier: IdentNode) extends
  AstNode {
}

trait AssignmentLeftNode extends AstNode {
  def getType: TypeNode
}

case class ArgListNode(val exprs: IndexedSeq[ExprNode]) extends AstNode {
}

trait PairElemNode extends AssignmentLeftNode with AssignmentRightNode {
  def exprChild: ExprNode
}

case class FstNode(override val exprChild: ExprNode) extends PairElemNode {
  override def getType: TypeNode = exprChild.getType match {
    case PairTypeNode(fstElemType, _) => fstElemType.toTypeNode
    case _ => SemanticErrorLog.add("[Semantic Error] Identifier in fst expression is not a pair."); ErrorTypeNode()
  }
}

case class SndNode(override val exprChild: ExprNode) extends PairElemNode {
  override def getType: TypeNode = exprChild.getType match {
    case PairTypeNode(_, sndElemType) => sndElemType.toTypeNode
    case _ => SemanticErrorLog.add("[Semantic Error] Identifier in snd expression is not a pair."); ErrorTypeNode()
  }
}

case class IdentNode(val name: String) extends ExprNode with AssignmentLeftNode {
  var identType: Option[TypeNode] = None

  override def getType: TypeNode = identType.getOrElse(throw new RuntimeException("Fatal Error: Identifier not annotated."))
}

case class ArrayElemNode(val identifier: IdentNode, val exprs: IndexedSeq[ExprNode]) extends ExprNode with AssignmentLeftNode {
  override def getType: TypeNode = {
    identifier.getType match {
      case ArrayTypeNode(elemType) => elemType
      case StringTypeNode() => if (exprs.length == 1) CharTypeNode() else SemanticErrorLog.add(s"[Semantic Error] $identifier is not an array"); ErrorTypeNode()
      case _ => SemanticErrorLog.add(s"[Semantic Error] $identifier is not an array"); ErrorTypeNode()
    }
  }
}

case class ArrayLiteralNode(val values: IndexedSeq[ExprNode]) extends AssignmentRightNode {
  // Also need to check that each ExprNode here has a nodeType of IntTypeNode
  override def getType: TypeNode = {
    if (values.isEmpty) {
      ArrayTypeNode(AnyTypeNode())
    } else {
      val elemType: TypeNode = values(0).getType
      ArrayTypeNode(elemType)
    }
  }
}
