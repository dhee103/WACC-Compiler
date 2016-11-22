trait TypeNode extends AstNode {
  def toPairElemTypeNode: PairElemTypeNode
}

trait BaseTypeNode extends TypeNode with PairElemTypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = this
  override def toTypeNode: TypeNode = this
}

trait PairElemTypeNode extends AstNode {
  def toTypeNode: TypeNode
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
  override def toTypeNode: TypeNode = this
}

case class PairTypeNode(val firstElemType: PairElemTypeNode, val secondElemType: PairElemTypeNode) extends TypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = InnerPairTypeNode()
}

case class InnerPairTypeNode() extends PairElemTypeNode {
  override def toTypeNode: TypeNode = PairTypeNode(null, null) // watch out for this
}

case class ErrorTypeNode() extends TypeNode with PairElemTypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = this // does this cause any problems?
  override def toTypeNode: TypeNode = this
}