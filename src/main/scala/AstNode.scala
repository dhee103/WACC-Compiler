trait AstNode {

}

class ProgNode(val _statChild: StatNode, val _funcChildren: IndexedSeq[FuncNode]) extends AstNode {

  val funcChildren: Array[FuncNode] = _funcChildren.toArray
  val statChild: StatNode = _statChild

}


class FuncNode(val _typeSignature: TypeNode, val _identifier: IdentNode,
               val _paramList: ParamListNode, val _statement: StatNode)
  extends AstNode {

  var typeSignature: TypeNode = _typeSignature
  var identifier: IdentNode = _identifier
  var paramList: ParamListNode = _paramList
  var statement: StatNode = _statement



}

class ParamListNode(val _params: IndexedSeq[ParamNode]) extends AstNode {
  val params: Array[ParamNode] = _params.toArray
}

class ParamNode(val _variableType: TypeNode, val _identifier: IdentNode) extends
  AstNode {
  val variableType = _variableType
  val identifier = _identifier
}


trait AssignmentLeftNode extends AstNode {

}

class ArgListNode(val _exprChildren: IndexedSeq[ExprNode]) extends AstNode {

  val exprChildren: Array[ExprNode] = _exprChildren.toArray

}

trait PairElemNode extends AssignmentLeftNode with AssignmentRightNode {

  val exprChild: ExprNode

}

class FstNode(val exprChild: ExprNode) extends PairElemNode {

}

class SndNode(val exprChild: ExprNode) extends PairElemNode {

}


trait TypeNode extends AstNode {

}

trait BaseTypeNode extends TypeNode with PairElemTypeNode {

}

class IntTypeNode extends BaseTypeNode {
  override def equals(other: Any): Boolean = {
    other.isInstanceOf[IntTypeNode]
  }
}

class BoolTypeNode extends BaseTypeNode {
  override def equals(other: Any): Boolean = {
    other.isInstanceOf[BoolTypeNode]
  }
}

class CharTypeNode extends BaseTypeNode {
  override def equals(other: Any): Boolean = {
    other.isInstanceOf[CharTypeNode]
  }
}

class StringTypeNode extends BaseTypeNode {
  override def equals(other: Any): Boolean = {
    other.isInstanceOf[StringTypeNode]
  }
}

class ArrayTypeNode(val _elemType: TypeNode) extends PairElemTypeNode with TypeNode {

  val elemType: TypeNode = _elemType
}

class PairTypeNode(val _firstElemType: PairElemTypeNode, val _secondElemType: PairElemTypeNode) extends TypeNode {

  val firstElemType: PairElemTypeNode = _firstElemType
  val secondElemType: PairElemTypeNode = _secondElemType

}


trait PairElemTypeNode extends AstNode {

}

class InnerPairTypeNode extends PairElemTypeNode {

}

case class IdentNode(val name: String, override var nodeType: Option[TypeNode]) extends ExprNode with AssignmentLeftNode {

  // val name: String = _name

  // var typeVal: TypeNode = _

  //override def equals(that: Any): Boolean = that match {

    //case that: IdentNode => that.name == this.name && that.hashCode == this.hashCode
    //case _ => false
  //}

  //override def hashCode: Int = {

  //  val prime = 67

  //  var result: Int = 1

  //   result = prime * (result + name.length)

  //   result = result * prime + (if (name == null) 0 else name.hashCode)

  //   result
  //}

}


class ArrayElemNode(val _identifier: IdentNode, val _indices: IndexedSeq[ExprNode]) extends ExprNode with  AssignmentLeftNode {

  val identifier: IdentNode = _identifier
  val indices: Array[ExprNode] = _indices.toArray

}

class ArrayLiteralNode(val _values: IndexedSeq[ExprNode]) extends AssignmentRightNode {

  val values: Array[ExprNode] = _values.toArray

}
