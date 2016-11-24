trait TypeNode extends AstNode {
  def toPairElemTypeNode: PairElemTypeNode
  def isEquivalentTo(that: TypeNode): Boolean = (this == that) && !that.isInstanceOf[ErrorTypeNode]
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
  override def isEquivalentTo(that: TypeNode): Boolean = {
    (this == that) || (that == ArrayTypeNode(CharTypeNode()))
  }
}

case class ArrayTypeNode(val elemType: TypeNode) extends PairElemTypeNode with TypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = this
  override def toTypeNode: TypeNode = this

  override def isEquivalentTo(that: TypeNode): Boolean = {
    this match {
      case ArrayTypeNode(AnyTypeNode()) => that.isInstanceOf[ArrayTypeNode]
      case ArrayTypeNode(CharTypeNode()) => (this == that) || (that == StringTypeNode())
      case _ => that match {
        case ArrayTypeNode(thatType) => elemType.isEquivalentTo(thatType)
        case _ => false
      }
    }
  }
}

case class PairTypeNode(val firstElemType: PairElemTypeNode, val secondElemType: PairElemTypeNode) extends TypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = InnerPairTypeNode()

  override def isEquivalentTo(that: TypeNode): Boolean = {
    this match {
      case PairTypeNode(AnyTypeNode(), AnyTypeNode()) => that.isInstanceOf[PairTypeNode]
      case _ => this == that  || that == PairTypeNode(AnyTypeNode(), AnyTypeNode())
    }
  }
}

case class InnerPairTypeNode() extends PairElemTypeNode {
  override def toTypeNode: TypeNode = PairTypeNode(AnyTypeNode(), AnyTypeNode()) // watch out for this
}

case class ErrorTypeNode() extends TypeNode with PairElemTypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = this // does this cause any problems?
  override def toTypeNode: TypeNode = this
  override def isEquivalentTo(that: TypeNode): Boolean = false
}

case class AnyTypeNode() extends TypeNode with PairElemTypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = this
  override def toTypeNode: TypeNode = this
  override def isEquivalentTo(that: TypeNode): Boolean = !that.isInstanceOf[ErrorTypeNode]
}