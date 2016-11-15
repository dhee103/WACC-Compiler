object Annotate {

  private val sTable: SymbolTable = new SymbolTable(null)

  def annotateAST(ast: ProgNode): Unit = {
    annotateProgNode(ast, new SymbolTable(None))
  }

  def annotateProgNode(prog: ProgNode, topSymbolTable: SymbolTable): Unit = {
    annotateStatNode(prog.statChild, topSymbolTable)
  }

  def annotateStatNode(statement: StatNode, currentScopeSymbolTable: SymbolTable): Unit = {
    statement match {
      case stat: DeclarationNode => annotateDeclarationNode(stat, currentScopeSymbolTable)
      case stat: AssignmentNode  => annotateAssignmentNode(stat, currentScopeSymbolTable)
      case stat: ReadNode        => annotateReadNode(stat, currentScopeSymbolTable)
      case stat: FreeNode        => annotateFreeNode(stat, currentScopeSymbolTable)
      case stat: ReturnNode      => annotateReturnNode(stat, currentScopeSymbolTable)
      case stat: ExitNode        => annotateExitNode(stat, currentScopeSymbolTable)
      case stat: PrintNode       => annotatePrintNode(stat, currentScopeSymbolTable)
      case stat: PrintlnNode     => annotatePrintlnNode(stat, currentScopeSymbolTable)
      case stat: IfNode          => annotateIfNode(stat, new SymbolTable(Some(currentScopeSymbolTable)))
      case stat: WhileNode       => annotateWhileNode(stat, new SymbolTable(Some(currentScopeSymbolTable)))
      case stat: NewBeginNode    => annotateNewBeginNode(stat, new SymbolTable(Some(currentScopeSymbolTable)))
      case stat: SequenceNode    => annotateSequenceNode(stat, currentScopeSymbolTable)
      case stat: SkipStatNode    => println("Got a skip")
      case _: Any                => println("error")
    }
  }

  def annotateDeclarationNode(statement: DeclarationNode, currentST:
  SymbolTable): Unit = {
    try {
      currentST.lookup(statement.identifier)
    } catch {
      case e: RuntimeException =>
        val ident: IdentNode = statement.identifier
        ident.nodeType = statement.variableType
        currentST.add(ident, ident.nodeType)
      case a: Any => throw a
    }
    annotateAssignmentRightNode(statement.rhs, currentST)
  }

  def annotateAssignmentNode(statement: AssignmentNode, currentST:
  SymbolTable): Unit = {
    annotateAssignmentLeftNode(statement.lhs, currentST)
    annotateAssignmentRightNode(statement.rhs, currentST)
  }

  def annotateReadNode(statement: ReadNode, currentST: SymbolTable): Unit = {
    annotateAssignmentLeftNode(statement.variable, currentST)
  }

  def annotateFreeNode(statement: FreeNode, currentST: SymbolTable): Unit = {
    annotateExprNode(statement.variable, currentST)
  }

  def annotateReturnNode(statement: ReturnNode, currentST: SymbolTable): Unit = {
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
    annotateStatNode(statement.fstStat, currentST)
    annotateStatNode(statement.sndStat, currentST)
  }

  def annotateAssignmentLeftNode(statement: AssignmentLeftNode, currentST: SymbolTable): Unit = {
    statement match {
      case lhs: IdentNode     => annotateIdentNode(lhs, currentST)
      case lhs: ArrayElemNode => annotateArrayElemNode(lhs, currentST)
      case lhs: PairElemNode  => annotatePairElemNode(lhs, currentST)
      case _: Any             => println("error")
    }
  }

  def annotateAssignmentRightNode(statement: AssignmentRightNode, currentST: SymbolTable): Unit = {
    statement match {
      case rhs: ExprNode         => annotateExprNode(rhs, currentST)
      case rhs: ArrayLiteralNode => annotateArrayLiteralNode(rhs, currentST)
      case rhs: NewPairNode      => annotateNewPairNode(rhs, currentST)
      case rhs: PairElemNode     => annotatePairElemNode(rhs, currentST)
      case rhs: CallNode         => annotateCallNode(rhs, currentST)
      case rhs: ArgListNode      => annotateArgListNode(rhs, currentST)
      case _: Any                => println("error")
    }
  }

  def annotateNewPairNode(node: NewPairNode, currentST: SymbolTable): Unit = {
    annotateExprNode(node.fstElem, currentST)
    annotateExprNode(node.sndElem, currentST)
  }

  // TODO: Implement functionTable; annotateCallNode should get return type of function by looking up ident in functionTable
  def annotateCallNode(call: CallNode, currentST: SymbolTable): Unit = {
    // annotateIdentNode()
    println("we love you mark")
    throw new UnsupportedOperationException("No support for annotating calls.")
  }

  def annotateArgListNode(argList: ArgListNode, currentST: SymbolTable): Unit = {
    for (expr <- argList.exprs) {
      annotateExprNode(expr, currentST)
    }
  }

  def annotatePairElemNode(pairStatement: PairElemNode, currentST:
  SymbolTable): Unit = {
    annotateExprNode(pairStatement.exprChild, currentST)
  }

  def annotateExprNode(expression: ExprNode, currentScopeSymbolTable: SymbolTable): Unit = {
    expression match {
      case expr: IdentNode           => annotateIdentNode(expr, currentScopeSymbolTable)
      case expr: ArrayElemNode       => annotateArrayElemNode(expr, currentScopeSymbolTable)
      case expr: UnaryOperationNode  => annotateUnaryOperationNode(expr, currentScopeSymbolTable)
      case expr: BinaryOperationNode => annotateBinaryOperationNode(expr, currentScopeSymbolTable)
      case _: IntLiteralNode
         | _: BoolLiteralNode
         | _: CharLiteralNode
         | _: StringLiteralNode
         | _: PairLiteralNode        => println("got a literal")
      case _: Any                    => println("error")
    }
  }

  def annotateUnaryOperationNode(unOpNode: UnaryOperationNode, currentST:
  SymbolTable) = {
      annotateExprNode(unOpNode.argument, currentST)
  }

  def annotateBinaryOperationNode(binOpNode: BinaryOperationNode, currentST:
  SymbolTable) = {
    annotateExprNode(binOpNode.leftExpr, currentST)
    annotateExprNode(binOpNode.rightExpr, currentST)
  }

  def annotateIdentNode(identifier: IdentNode, currentST: SymbolTable): Unit = {
    try {
      identifier.nodeType = currentST.lookupAll(identifier)
    } catch {
      case e: Exception => println("add to error log"); throw e
    }
  }

  def annotateArrayElemNode(arrElem: ArrayElemNode, currentST: SymbolTable): Unit = {
    annotateIdentNode(arrElem.identifier, currentST)

    for (index <- arrElem.exprs) {
      annotateExprNode(index, currentST)
    }
  }

  def annotateArrayLiteralNode(arrNode: ArrayLiteralNode, currentST: SymbolTable): Unit = {
    for (value <- arrNode.values) {
      annotateExprNode(value, currentST)
    }
  }

// helper function
  def checkNodesHaveSameType(typeNode1: TypeNode, typeNode2: TypeNode):
  Boolean = {
    def f[A, B: ClassTag](a: A, b: B) = a match {
      case _: B => true
      case _    => false
    }
    f(typeNode1, typeNode2)
  }

  def getType[T](obj: T): String = {
    val str: String = obj.getClass.toString
    str.substring(6, str.length)
  }

}
