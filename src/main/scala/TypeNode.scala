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
  override def toString: String = "int"
}

case class BoolTypeNode() extends BaseTypeNode {
  override def toString: String = "bool"
}

case class CharTypeNode() extends BaseTypeNode {
  override def toString: String = "char"
}

case class StringTypeNode() extends BaseTypeNode {
  override def isEquivalentTo(that: TypeNode): Boolean = {
    (this == that) || (that == ArrayTypeNode(CharTypeNode()))
  }
  override def toString: String = "string"
}

case class ArrayTypeNode(elemType: TypeNode) extends PairElemTypeNode with TypeNode {
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
  override def toString: String = s"${elemType.toString}[]"
}

case class PairTypeNode(firstElemType: PairElemTypeNode, secondElemType: PairElemTypeNode) extends TypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = InnerPairTypeNode()

  override def isEquivalentTo(that: TypeNode): Boolean = {
    this match {
      case PairTypeNode(AnyTypeNode(), AnyTypeNode()) => that.isInstanceOf[PairTypeNode]
      case _ => this == that  || that == PairTypeNode(AnyTypeNode(), AnyTypeNode())
    }
  }
  override def toString: String = s"pair(${firstElemType.toString},${secondElemType.toString})"
}

case class InnerPairTypeNode() extends PairElemTypeNode {
  override def toTypeNode: TypeNode = PairTypeNode(AnyTypeNode(), AnyTypeNode()) // watch out for this
  override def toString: String = "pair"
}

case class ErrorTypeNode() extends TypeNode with PairElemTypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = this // does this cause any problems?
  override def toTypeNode: TypeNode = this
  override def isEquivalentTo(that: TypeNode): Boolean = false
  override def toString: String = "error"
}

case class AnyTypeNode() extends TypeNode with PairElemTypeNode {
  override def toPairElemTypeNode: PairElemTypeNode = this
  override def toTypeNode: TypeNode = this
  override def isEquivalentTo(that: TypeNode): Boolean = !that.isInstanceOf[ErrorTypeNode]
}