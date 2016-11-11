import scala.reflect.ClassTag

class Annotate(val _AST: ProgNode) {

  private var AST = _AST

  private val STable: SymbolTable = new SymbolTable(null)

  //def getAnnotatedAST

  def annotateStatNode(statement: StatNode, currentScopeSymbolTable:
  SymbolTable): Unit = {

    val statementType: String = Annotate.getType(statement)

    statementType match {

      case "DeclarationNode" => annotateDeclarationNode(statement
        .asInstanceOf[DeclarationNode], STable)
      case "AssignmentNode" => annotateAssignmentNode(statement
        .asInstanceOf[AssignmentNode], STable)
      case "ReadNode" => annotateReadNode(statement.asInstanceOf[ReadNode],
        STable)
      case "FreeNode" => annotateFreeNode(statement.asInstanceOf[FreeNode],
        STable)
      case "ReturnNode" => annotateReturnNode(statement
        .asInstanceOf[ReturnNode], STable)
      case "ExitNode" => annotateExitNode(statement.asInstanceOf[ExitNode],
        STable)
      case "PrintNode" => annotatePrintNode(statement
        .asInstanceOf[PrintNode], STable)
      case "PrintlnNode" => annotatePrintlnNode(statement
        .asInstanceOf[PrintlnNode], STable)
      case "IfNode" => annotateIfNode(statement.asInstanceOf[IfNode], new
          SymbolTable(STable))
      case "WhileNode" => annotateWhileNode(statement
        .asInstanceOf[WhileNode], new SymbolTable(STable))
      case "NewBeginNode" => annotateNewBeginNode(statement
        .asInstanceOf[NewBeginNode], new SymbolTable(STable))
      case "SequenceNode" => annotateSequenceNode(statement
        .asInstanceOf[SequenceNode], STable)

    }
  }

  def annotateExprNode(expression: ExprNode, currentScopeSymbolTable:
  SymbolTable): Unit = {

    val exprType: String = Annotate.getType(expression)

    exprType match {

      case "IdentNode" => annotateIdentNode(expression
        .asInstanceOf[IdentNode], currentScopeSymbolTable)

      case "LogicalNotNode" => annotateUnaryOperationNode(expression
        .asInstanceOf[LogicalNotNode], currentScopeSymbolTable)
      case "OrdNode" => annotateUnaryOperationNode(expression
        .asInstanceOf[OrdNode], currentScopeSymbolTable)
      case "ChrNode" => annotateUnaryOperationNode(expression
        .asInstanceOf[ChrNode], currentScopeSymbolTable)
      case "LenNode" => annotateUnaryOperationNode(expression
        .asInstanceOf[LenNode], currentScopeSymbolTable)
      case "NegativeNode" => annotateUnaryOperationNode(expression
        .asInstanceOf[NegativeNode], currentScopeSymbolTable)

      case "MulOperationNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[MulNode], currentScopeSymbolTable)
      case "DivOperationNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[DivNode], currentScopeSymbolTable)
      case "ModNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[ModNode], currentScopeSymbolTable)
      case "PlusNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[PlusNode], currentScopeSymbolTable)
      case "MinusNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[MinusNode], currentScopeSymbolTable)
      case "GreaterThanNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[GreaterThanNode], currentScopeSymbolTable)
      case "GreaterEqualNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[GreaterEqualNode], currentScopeSymbolTable)
      case "LessThanNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[LessThanNode], currentScopeSymbolTable)
      case "LessEqualNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[LessEqualNode], currentScopeSymbolTable)
      case "DoubleEqualNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[DoubleEqualNode], currentScopeSymbolTable)
      case "NotEqualNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[NotEqualNode], currentScopeSymbolTable)
      case "LogicalOrNode" => annotateBinaryOperationNode(expression
        .asInstanceOf[LogicalOrNode], currentScopeSymbolTable)

//        TODO: add node for '(' <expr> ')'?
    }

  }

  def annotateIdentNode(identifier: IdentNode, currentST: SymbolTable): Unit = {
    identifier.nodeType = currentST.lookupAll(identifier)
    //may throw exceptions here

  }

  def annotateUnaryOperationNode(unOPNode: UnaryOperationNode, currentST:
  SymbolTable) = {
      annotateExprNode(unOPNode.argument, currentST)
  }

  def annotateBinaryOperationNode(binOpNode: BinaryOperationNode, currentST:
  SymbolTable) = {
    annotateExprNode(binOpNode.leftExpr, currentST)
    annotateExprNode(binOpNode.rightExpr, currentST)
  }

  def annotateDeclarationNode(statement: DeclarationNode, currentST:
  SymbolTable): Unit = {

    val ident: IdentNode = statement.identifier

    ident.nodeType = statement.variableType

    currentST.add(ident, ident.nodeType)

    statement.identifier = ident

  }

  def annotateAssignmentNode(statement: AssignmentNode, currentST:
  SymbolTable): Unit = {

    var lhsNode: IdentNode = null

    //    var rhsNode: AstNode = null

    if (Annotate.getType(statement.lhs) == "IdentNode") {

      lhsNode = statement.lhs.asInstanceOf[IdentNode]

      lhsNode.nodeType = currentST.lookupAll(lhsNode)

    }

    //    if (getType(statement.rhs) == "IdentNode") {
    //
    //      rhsNode = statement.rhs.asInstanceOf[IdentNode]
    //
    //      rhsNode.nodeType = currentST.lookupAll(rhsNode)
    //
    //    }

    Annotate.getType(statement.rhs) match {
      case "ExprNode" => annotateExprNode(statement.asInstanceOf[ExprNode],
        currentST)
      case "ArrayLiteralNode" =>
      case "NewPairNode" => annotateExprNode(statement
        .asInstanceOf[NewPairNode].fstElem, currentST);
        annotateExprNode(statement.asInstanceOf[NewPairNode].sndElem, currentST)
      case "PairElemNode" => annotatePairElemNode(statement
        .asInstanceOf[PairElemNode], currentST)
      case "CallNode" => annotateIdentNode(statement.asInstanceOf[IdentNode],
        currentST); //TODO: add arglist?
      case _: Any => println("error")
    }

//    statement.rhs = rhsNode

    statement.lhs = lhsNode

  }

  def annotateReadNode(statement: ReadNode, currentST: SymbolTable): Unit = {

    var readTarget: IdentNode = null

    if (Annotate.getType(statement.variable) == "IdentNode") {

      readTarget = statement.variable.asInstanceOf[IdentNode]

      readTarget.nodeType = currentST.lookupAll(readTarget)

    }

  }

  def annotateFreeNode(statement: FreeNode, currentST: SymbolTable): Unit = {
    annotateExprNode(statement.variable, currentST)
  }

  def annotateReturnNode(statement: ReturnNode, currentST: SymbolTable): Unit
  = {
    annotateExprNode(statement.returnValue, currentST)
  }

  def annotateExitNode(statement: ExitNode, currentST: SymbolTable): Unit = {
    annotateExprNode(statement.exitCode, currentST)
  }

  def annotatePrintNode(statement: PrintNode, currentST: SymbolTable): Unit = {
    annotateExprNode(statement.text,  currentST)
  }

  def annotatePrintlnNode(statement: PrintlnNode, currentST: SymbolTable):
  Unit = {
    annotateExprNode(statement.text, currentST)
  }

  def annotateIfNode(statement: IfNode, currentST: SymbolTable): Unit = {
    annotateExprNode(statement.condition, currentST)
    annotateStatNode(statement.thenStat, currentST)
    annotateStatNode(statement.elseStat, currentST)
  }

  def annotateWhileNode(statement: WhileNode, currentST: SymbolTable): Unit = {
    annotateExprNode(statement.condition, currentST)
    annotateStatNode(statement.loopBody, currentST)
  }

  def annotateNewBeginNode(statement: NewBeginNode, currentST: SymbolTable):
  Unit = {
    annotateStatNode(statement.body, currentST)
  }

  def annotateSequenceNode(statement: SequenceNode, currentST: SymbolTable):
  Unit = {

  }

//  TODO: check if this is done?
  def annotatePairElemNode(pairStatement: PairElemNode, currentST:
  SymbolTable): Unit = {
    annotateExprNode(pairStatement.exprChild, currentST)
  }




  def checkNodesHaveSameType(typeNode1: TypeNode, typeNode2: TypeNode):
  Boolean = {
    def f[A, B: ClassTag](a: A, b: B) = a match {
      case _: B => true
      case _ => false
    }
    f(typeNode1, typeNode2)
  }


}

object Annotate {
  def getType[T](obj: T): String = {
    val str: String = obj.getClass().toString
    str.substring(6, str.length)
  }
}