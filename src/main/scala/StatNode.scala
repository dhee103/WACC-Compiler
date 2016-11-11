trait StatNode extends AstNode {

}

class SkipStatNode extends StatNode {

}

class DeclarationNode(val _variableType: TypeNode, val _identifier: IdentNode, val _rhs: AssignmentRightNode) extends StatNode {

  val variableType: TypeNode = _variableType
  var identifier: IdentNode = _identifier
  val rhs: AssignmentRightNode = _rhs

}

class AssignmentNode(val _lhs: AssignmentLeftNode, val _rhs: AssignmentRightNode) extends StatNode {

  var lhs: AssignmentLeftNode = _lhs
  var rhs: AssignmentRightNode = _rhs

}

class ReadNode(val _variable: AssignmentLeftNode) extends StatNode {

  val variable: AssignmentLeftNode = _variable

}

class FreeNode(val _variable: ExprNode) extends StatNode {

  val variable: ExprNode = _variable

}

class ReturnNode(val _returnValue: ExprNode) extends StatNode {

  val returnValue: ExprNode = _returnValue

}

class ExitNode(val _exitCode: ExprNode) extends StatNode {

  val exitCode: ExprNode = _exitCode

}

class PrintNode(val _text: ExprNode) extends StatNode {

  val text: ExprNode = _text

}

class PrintlnNode(val _text: ExprNode) extends StatNode {

  val text: ExprNode = _text

}

class IfNode (val _condition: ExprNode, val _thenStat: StatNode, val _elseStat: StatNode) extends StatNode {

  val condition: ExprNode = _condition
  val thenStat: StatNode = _thenStat
  val elseStat: StatNode = _elseStat

}

class WhileNode(val _condition: ExprNode, val _loopBody: StatNode) extends StatNode {

  val condition: ExprNode = _condition
  val loopBody: StatNode = _loopBody

}

class NewBeginNode(val _body: StatNode) extends StatNode {

  val body: StatNode = _body

}

class SequenceNode(val _fstStat: StatNode, val _sndStat: StatNode) extends StatNode {

  val fstStat: StatNode = _fstStat
  val sndStat: StatNode = _sndStat

}
