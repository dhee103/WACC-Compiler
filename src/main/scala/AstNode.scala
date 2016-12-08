import scala.collection.mutable.MutableList

trait AstNode {
}

trait ScopeExtender {
  val scopeSizes = MutableList[Int]()
  var symbols = MutableList[List[IdentNode]]()
}

case class ProgNode(statChild: StatNode, funcChildren: IndexedSeq[FuncNode]) extends AstNode with ScopeExtender {
}

case class FuncNode(returnType: TypeNode, identifier: IdentNode,
                    paramList: ParamListNode, statement: StatNode, var noOfLocalVars: Int = 0)
  extends AstNode {
  var localVars: List[IdentNode] = _
}

case class ParamListNode(params: IndexedSeq[ParamNode]) extends AstNode {
}

case class ParamNode(variableType: TypeNode, identifier: IdentNode) extends
  AstNode {
}

trait AssignmentLeftNode extends AstNode {
  def getType: TypeNode
}

case class ArgListNode(exprs: IndexedSeq[ExprNode]) extends AstNode {
}

trait PairElemNode extends AssignmentLeftNode with AssignmentRightNode {
  def exprChild: ExprNode
}

case class FstNode(override val exprChild: ExprNode) extends PairElemNode {
  override def getType: TypeNode = exprChild.getType match {
    case PairTypeNode(fstElemType, _) => fstElemType.toTypeNode
    case _ => SemanticErrorLog.add("Identifier in fst expression is not a pair."); ErrorTypeNode()
  }
}

case class SndNode(override val exprChild: ExprNode) extends PairElemNode {
  override def getType: TypeNode = exprChild.getType match {
    case PairTypeNode(_, sndElemType) => sndElemType.toTypeNode
    case _ => SemanticErrorLog.add("Identifier in snd expression is not a pair."); ErrorTypeNode()
  }
}

case class IdentNode(name: String) extends ExprNode with AssignmentLeftNode {
  var identType: Option[TypeNode] = None

  override def getType: TypeNode = identType.getOrElse(throw new RuntimeException("Fatal Error: Identifier not annotated."))
}

case class ArrayElemNode(identifier: IdentNode, exprs: IndexedSeq[ExprNode]) extends ExprNode with AssignmentLeftNode {
  override def getType: TypeNode = {
    identifier.getType match {
      case t: ArrayTypeNode => unwrapArrayType(t, exprs.size)
      case StringTypeNode() => if (exprs.length == 1) CharTypeNode() else {SemanticErrorLog.add(s"$identifier is not an array"); ErrorTypeNode()}
      case _ => SemanticErrorLog.add(s"$identifier is not an array"); ErrorTypeNode()
    }
  }

  private def unwrapArrayType(arrayType: ArrayTypeNode, unwrapCount: Int): TypeNode = {
    var elemType: TypeNode = arrayType
    for (i <- 1 to unwrapCount) {
      elemType = elemType.asInstanceOf[ArrayTypeNode].elemType
    }

    elemType
  }
}

case class ArrayLiteralNode(values: IndexedSeq[ExprNode]) extends AssignmentRightNode {
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
