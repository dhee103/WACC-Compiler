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

}

case class FstNode(override val exprChild: ExprNode) extends PairElemNode {
}

case class SndNode(override val exprChild: ExprNode) extends PairElemNode {
}

trait TypeNode extends AstNode {
}

trait BaseTypeNode extends TypeNode with PairElemTypeNode {
}

case class IntTypeNode() extends BaseTypeNode {
}

case class BoolTypeNode() extends BaseTypeNode {
}

case class CharTypeNode() extends BaseTypeNode {
}

case class StringTypeNode() extends BaseTypeNode {
}

case class ArrayTypeNode(val elemType: TypeNode) extends PairElemTypeNode with TypeNode {
}

case class PairTypeNode(val firstElemType: PairElemTypeNode, val secondElemType: PairElemTypeNode) extends TypeNode {
}

trait PairElemTypeNode extends AstNode {
}

case class InnerPairTypeNode() extends PairElemTypeNode {
}

case class IdentNode(val name: String) extends ExprNode with AssignmentLeftNode {
  nodeType = None
}

case class ArrayElemNode(val identifier: IdentNode, val exprs: IndexedSeq[ExprNode]) extends ExprNode with  AssignmentLeftNode {
}

case class ArrayLiteralNode(val values: IndexedSeq[ExprNode]) extends AssignmentRightNode {
}
