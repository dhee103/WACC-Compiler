trait AstNode extends AstNode {

}

class ProgNode() extends AstNode {

  val funcChildren: Array[FuncNode]
  val statChild: StatNode

}


class FuncNode extends AstNode {
}

trait AssignmentLeftNode extends AstNode {

}

class ArgListNode extends AstNode {

}

class PairElemNode extends AssignmentLeftNode with AssignmentRightNode {

}

class FstNode extends PairElemNode {

}

class SndNode extends PairElemNode {

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

class ArrayTypeNode extends TypeNode with PairElemTypeNode {

}

class PairTypeNode extends TypeNode {

}


class PairElemTypeNode extends AstNode {

}

class InnerPairTypeNode extends PairElemTypeNode {

}

class IdentNode extends ExprNode with AssignmentLeftNode {

}


class ArrayElemNode extends ExprNode with  AssignmentLeftNode {

}

class ArrayLiteralNode extends AssignmentRightNode {

}
