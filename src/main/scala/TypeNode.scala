trait TypeNode extends AstNode {
  def toPairElemTypeNode: PairElemTypeNode
}

trait BaseTypeNode extends TypeNode with PairElemTypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = this
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
  override def toPairElemTypeNode: PairElemTypeNode = this
}

case class PairTypeNode(val firstElemType: PairElemTypeNode, val secondElemType: PairElemTypeNode) extends TypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = InnerPairTypeNode()
}

trait PairElemTypeNode extends AstNode {
}

case class InnerPairTypeNode() extends PairElemTypeNode {
}