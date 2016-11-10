trait AstNode {

}

class ProgNode(val _statChild: StatNode, val _funcChildren: IndexedSeq[FuncNode]) extends AstNode {

  val funcChildren: Array[FuncNode] = _funcChildren.toArray
  val statChild: StatNode = _statChild

}


class FuncNode extends AstNode {
}

trait AssignmentLeftNode extends AstNode {

}

class ArgListNode(val _exprChildren: ExprNode*) extends AstNode {

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

}

class BoolTypeNode extends BaseTypeNode {

}

class CharTypeNode extends BaseTypeNode {

}

class StringTypeNode extends BaseTypeNode {

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

class IdentNode(val _name: String, val _type: TypeNode) extends ExprNode with AssignmentLeftNode {

  val name: String = _name
  val typeVal: TypeNode = _type

  override def equals(that: Any): Boolean = that match {

    case that: IdentNode => that.name == this.name && that.hashCode == this.hashCode
    case _ => false
  }

  override def hashCode: Int = {

    var prime = 67

    var result: Int = 1

     result = prime * (result + (name.length))

     result = result * prime + (if (name == null) 0 else name.hashCode)

     result
  }

}


class ArrayElemNode(val _identifier: IdentNode, val _indices: Array[ExprNode]) extends ExprNode with  AssignmentLeftNode {

  val identifier: IdentNode = _identifier
  val indices: Array[ExprNode] = _indices

}

class ArrayLiteralNode(val _values: Array[ExprNode]) extends AssignmentRightNode {

  val values: Array[ExprNode] = _values

}
