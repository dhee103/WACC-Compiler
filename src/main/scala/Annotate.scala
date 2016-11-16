object Annotate {

  var numSemanticErrors: Int = 0

  def getNumberOfSemanticErrors(): Int = numSemanticErrors

  def annotateAST(ast: ProgNode): Unit = {
    annotateProgNode(ast, new SymbolTable(None), new FunctionTable())
  }

  def annotateProgNode(prog: ProgNode, topSymbolTable: SymbolTable, functionTable: FunctionTable): Unit = {
    for (f <- prog.funcChildren) {
      functionTable.add(f)
    }
    annotateStatNode(prog.statChild, topSymbolTable, functionTable)
  }

  def annotateStatNode(statement: StatNode, currentScopeSymbolTable: SymbolTable, functionTable: FunctionTable): Unit = {
    statement match {
      case stat: DeclarationNode => annotateDeclarationNode(stat, currentScopeSymbolTable, functionTable)
      case stat: AssignmentNode  => annotateAssignmentNode(stat, currentScopeSymbolTable, functionTable)
      case stat: ReadNode        => annotateReadNode(stat, currentScopeSymbolTable)
      case stat: FreeNode        => annotateFreeNode(stat, currentScopeSymbolTable)
      case stat: ReturnNode      => annotateReturnNode(stat, currentScopeSymbolTable)
      case stat: ExitNode        => annotateExitNode(stat, currentScopeSymbolTable)
      case stat: PrintNode       => annotatePrintNode(stat, currentScopeSymbolTable)
      case stat: PrintlnNode     => annotatePrintlnNode(stat, currentScopeSymbolTable)
      case stat: IfNode          => annotateIfNode(stat, new SymbolTable(Some(currentScopeSymbolTable)), functionTable)
      case stat: WhileNode       => annotateWhileNode(stat, new SymbolTable(Some(currentScopeSymbolTable)), functionTable)
      case stat: NewBeginNode    => annotateNewBeginNode(stat, new SymbolTable(Some(currentScopeSymbolTable)), functionTable)
      case stat: SequenceNode    => annotateSequenceNode(stat, currentScopeSymbolTable, functionTable)
      case stat: SkipStatNode    =>
      case _: Any                => println("error"); numSemanticErrors += 1
    }
  }

  def annotateDeclarationNode(statement: DeclarationNode, currentST: SymbolTable, functionTable: FunctionTable): Unit = {
    if (currentST.doesContain(statement.identifier)) {
      // throw some error
      println("[Semantic Error]: Redeclaration error")
      numSemanticErrors += 1
    } else {
      val ident: IdentNode = statement.identifier
      ident.nodeType = Some(statement.variableType)
      currentST.add(ident, ident.nodeType.getOrElse(throw new RuntimeException("Fatal error")))
      annotateAssignmentRightNode(statement.rhs, currentST, functionTable)
    }
  }

  def annotateAssignmentNode(statement: AssignmentNode, currentST: SymbolTable, functionTable: FunctionTable): Unit = {
    annotateAssignmentLeftNode(statement.lhs, currentST)
    annotateAssignmentRightNode(statement.rhs, currentST, functionTable)
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

  def annotateIfNode(statement: IfNode, currentST: SymbolTable, functionTable: FunctionTable): Unit = {
    annotateExprNode(statement.condition, currentST)
    annotateStatNode(statement.thenStat, currentST, functionTable)
    annotateStatNode(statement.elseStat, currentST, functionTable)
  }

  def annotateWhileNode(statement: WhileNode, currentST: SymbolTable, functionTable: FunctionTable): Unit = {
    annotateExprNode(statement.condition, currentST)
    annotateStatNode(statement.loopBody, currentST, functionTable)
  }

  def annotateNewBeginNode(statement: NewBeginNode, currentST: SymbolTable, functionTable: FunctionTable):
  Unit = {
    annotateStatNode(statement.body, currentST, functionTable)
  }

  def annotateSequenceNode(statement: SequenceNode, currentST: SymbolTable, functionTable: FunctionTable):
  Unit = {
    annotateStatNode(statement.fstStat, currentST, functionTable)
    annotateStatNode(statement.sndStat, currentST, functionTable)
  }

  def annotateAssignmentLeftNode(statement: AssignmentLeftNode, currentST: SymbolTable): Unit = {
    statement match {
      case lhs: IdentNode     => annotateIdentNode(lhs, currentST)
      case lhs: ArrayElemNode => annotateArrayElemNode(lhs, currentST)
      case lhs: PairElemNode  => annotatePairElemNode(lhs, currentST)
      case _: Any             => println("error"); numSemanticErrors += 1
    }
  }

  def annotateAssignmentRightNode(statement: AssignmentRightNode, currentST: SymbolTable, functionTable: FunctionTable): Unit = {
    statement match {
      case rhs: ExprNode         => annotateExprNode(rhs, currentST)
      case rhs: ArrayLiteralNode => annotateArrayLiteralNode(rhs, currentST)
      case rhs: NewPairNode      => annotateNewPairNode(rhs, currentST)
      case rhs: PairElemNode     => annotatePairElemNode(rhs, currentST)
      case rhs: CallNode         => annotateCallNode(rhs, currentST, functionTable)
      case rhs: ArgListNode      => annotateArgListNode(rhs, currentST)
      case _: Any                => println("error"); numSemanticErrors += 1
    }
  }

  def annotateNewPairNode(node: NewPairNode, currentST: SymbolTable): Unit = {
    annotateExprNode(node.fstElem, currentST)
    annotateExprNode(node.sndElem, currentST)
  }

  // TODO: Implement functionTable; annotateCallNode should get return type of function by looking up ident in functionTable
  def annotateCallNode(call: CallNode, currentST: SymbolTable, functionTable: FunctionTable): Unit = {

    val identifier = call.id
    identifier.nodeType = Some(functionTable.getReturnType(identifier))

    call.argList match {
      case None =>
      case Some(args) => annotateArgListNode(args, currentST)
    }

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
         | _: PairLiteralNode        =>
      case _: Any                    => println("error"); numSemanticErrors += 1
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
      identifier.nodeType = Some(currentST.lookupAll(identifier))
    } catch {
      case e: Exception => println(s"[Semantic Error]: Variable $identifier.nodeType doesn't exist"); numSemanticErrors += 1; throw e
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

// helper
// function
// import ClassTag
  // def checkNodesHaveSameType(typeNode1: TypeNode, typeNode2: TypeNode):
  // Boolean = {
  //   def f[A, B: ClassTyearag](a: A, b: B) = a match {
  //     case _: B => true
  //     case _    => false
  //   }
  //   f(typeNode1, typeNode2)
  // }

  def getType[T](obj: T): String = {
    val str: String = obj.getClass.toString
    str.substring(6, str.length)
  }

}
