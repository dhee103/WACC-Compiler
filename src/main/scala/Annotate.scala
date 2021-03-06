import scala.collection.mutable.MutableList

object Annotate {

  def annotateAST(ast: ProgNode): Unit = {
    annotateProgNode(ast, new SymbolTable(None))
  }

  def annotateProgNode(prog: ProgNode, topSymbolTable: SymbolTable): Unit = {
    for (f <- prog.funcChildren) {
      FunctionTable.add(f)
    }
    for (f <- prog.funcChildren) {
      annotateFuncNode(f, new SymbolTable(Some(topSymbolTable)))
    }
    annotateStatNode(prog.statChild, topSymbolTable, isInMain = true, canHaveBreak = false)
    prog.symbols = MutableList(topSymbolTable.symbols)
    prog.scopeSizes += topSymbolTable.size
  }

  def annotateFuncNode(function: FuncNode, currentScopeSymbolTable: SymbolTable): Unit = {
    // go through parameters and add all to symbol table
    for (param <- function.paramList.params) {
      currentScopeSymbolTable.add(param.identifier, param.variableType)
    }
    val noOfParameters = currentScopeSymbolTable.size
    val parameters = currentScopeSymbolTable.symbols

    annotateStatNode(function.statement, currentScopeSymbolTable, isInMain = false, canHaveBreak = false)

    function.noOfLocalVars = currentScopeSymbolTable.size - noOfParameters
    println(s"Function ${function.identifier.name} has the following in its symbol table:") //DEBUG
    currentScopeSymbolTable.symbols.foreach(println) //DEBUG
    function.localVars = currentScopeSymbolTable.symbols.filterNot(parameters.toSet)
    println(s"Function ${function.identifier.name} has the following local vars:") //DEBUG
    function.localVars.foreach(println) //DEBUG
  }

  def annotateStatNode(statement: StatNode, currentScopeSymbolTable: SymbolTable, isInMain: Boolean, canHaveBreak: Boolean): Unit = {
    statement match {
      case stat: DeclarationNode => annotateDeclarationNode(stat, currentScopeSymbolTable)
      case stat: AssignmentNode => annotateAssignmentNode(stat, currentScopeSymbolTable)
      case stat: ReadNode => annotateReadNode(stat, currentScopeSymbolTable)
      case stat: FreeNode => annotateFreeNode(stat, currentScopeSymbolTable)
      case stat: ReturnNode => annotateReturnNode(stat, currentScopeSymbolTable, isInMain)
      case stat: ExitNode => annotateExitNode(stat, currentScopeSymbolTable)
      case stat: PrintNode => annotatePrintNode(stat, currentScopeSymbolTable)
      case stat: PrintlnNode => annotatePrintlnNode(stat, currentScopeSymbolTable)
      case stat: IfNode => annotateIfNode(stat, currentScopeSymbolTable, isInMain, canHaveBreak)
      case stat: WhileNode => annotateWhileNode(stat, new SymbolTable(Some(currentScopeSymbolTable)), isInMain, canHaveBreak)
      case stat: NewBeginNode => annotateNewBeginNode(stat, new SymbolTable(Some(currentScopeSymbolTable)), isInMain, canHaveBreak)
      case stat: SequenceNode => annotateSequenceNode(stat, currentScopeSymbolTable, isInMain, canHaveBreak)
      case stat: SkipStatNode => // Nothing needs to be done
      case stat: BreakNode => annotateBreakNode(isInMain, canHaveBreak)
      case stat: SwitchNode => annotateSwitchNode(stat, new SymbolTable(Some(currentScopeSymbolTable)), isInMain, canHaveBreak)
      //      case _: Any                => println("error"); numSemanticErrors += 1
    }
  }

  def annotateDeclarationNode(statement: DeclarationNode, currentST: SymbolTable): Unit = {
    val ident: IdentNode = statement.identifier
    if (currentST.doesContain(ident)) {
      val varName = ident.name
      SemanticErrorLog.add(s"Attempted to redeclare variable $varName")
    } else {
      ident.identType = Some(statement.variableType)
      currentST.add(ident, ident.getType)
      annotateAssignmentRightNode(statement.rhs, currentST)
    }
  }

  def annotateAssignmentNode(statement: AssignmentNode, currentST: SymbolTable): Unit = {
    annotateAssignmentLeftNode(statement.lhs, currentST)
    annotateAssignmentRightNode(statement.rhs, currentST)
  }

  def annotateReadNode(statement: ReadNode, currentST: SymbolTable): Unit = {
    annotateAssignmentLeftNode(statement.variable, currentST)
  }

  def annotateFreeNode(statement: FreeNode, currentST: SymbolTable): Unit = {
    annotateExprNode(statement.variable, currentST)
  }

  def annotateReturnNode(statement: ReturnNode, currentST: SymbolTable, isInMain: Boolean): Unit = {
    annotateExprNode(statement.returnValue, currentST)

    if (isInMain) SemanticErrorLog.add("Return statement used outside of function definition.")
  }

  def annotateExitNode(statement: ExitNode, currentST: SymbolTable): Unit = {
    annotateExprNode(statement.exitCode, currentST)
  }

  def annotateBreakNode(isInMain: Boolean, canHaveBreak: Boolean): Unit = {
//    if (isInMain) SemanticErrorLog.add("\"break\" cannot be in main.")
    if (!canHaveBreak) SemanticErrorLog.add("\"break\" statement used outside of loop or switch case")
//    TODO: Edit message for consistency
  }

  def annotatePrintNode(statement: PrintNode, currentST: SymbolTable): Unit = {
    annotateExprNode(statement.text, currentST)
  }

  def annotatePrintlnNode(statement: PrintlnNode, currentST: SymbolTable):
  Unit = {
    annotateExprNode(statement.text, currentST)
  }

  def annotateIfNode(statement: IfNode, currentST: SymbolTable, isInMain: Boolean, canHaveBreak: Boolean): Unit = {
    annotateExprNode(statement.condition, currentST)
    val thenBranchST = new SymbolTable(Some(currentST))
    annotateStatNode(statement.thenStat, thenBranchST, isInMain, canHaveBreak)

    statement.scopeSizes += thenBranchST.size
    statement.symbols += thenBranchST.symbols

    //    annotate all elifs
    for ((cond, stat) <- statement.elifConds zip statement.elifStats) {
      annotateExprNode(cond, currentST)
      val elifBranchST = new SymbolTable(Some(currentST))
      annotateStatNode(stat, elifBranchST, isInMain, canHaveBreak)
      statement.scopeSizes += elifBranchST.size
      statement.symbols += elifBranchST.symbols
    }

    if (statement.elseStat.isDefined) {
      val elseBranchST = new SymbolTable(Some(currentST))
      annotateStatNode(statement.elseStat.get, elseBranchST, isInMain, canHaveBreak)
      statement.scopeSizes += elseBranchST.size
      statement.symbols += elseBranchST.symbols
    }

  }

  def annotateSwitchNode(statement: SwitchNode, currentST: SymbolTable, isInMain: Boolean, canHaveBreak: Boolean): Unit = {
//    TODO: check if this works when exprs and stats aren't of the same size (assume it does as it worked before default which would mean exprs > stats)

    for ((cond, stat) <- statement.exprChildren zip statement.statChildren) {
      annotateExprNode(cond, currentST)
      val caseST = new SymbolTable(Some(currentST))
      annotateStatNode(stat, caseST, isInMain, canHaveBreak = true)
      statement.scopeSizes += caseST.size
      statement.symbols += caseST.symbols
    }

  }

  def annotateWhileNode(statement: WhileNode, currentST: SymbolTable, isInMain: Boolean, canHaveBreak: Boolean): Unit = {
    annotateExprNode(statement.condition, currentST)
    annotateStatNode(statement.loopBody, currentST, isInMain, canHaveBreak = true)

    // New scope introduced in WhileNode
    statement.scopeSizes += currentST.size
    statement.symbols = MutableList(currentST.symbols)
  }

  def annotateNewBeginNode(statement: NewBeginNode, currentST: SymbolTable, isInMain: Boolean, canHaveBreak: Boolean):
  Unit = {
    annotateStatNode(statement.body, currentST, isInMain, canHaveBreak)

    // New scope introduced in NewBeginNode
    statement.scopeSizes += currentST.size
    statement.symbols = MutableList(currentST.symbols)
  }

  def annotateSequenceNode(statement: SequenceNode, currentST: SymbolTable, isInMain: Boolean, canHaveBreak: Boolean):
  Unit = {
    annotateStatNode(statement.fstStat, currentST, isInMain, canHaveBreak)
    annotateStatNode(statement.sndStat, currentST, isInMain, canHaveBreak)
  }

  def annotateAssignmentLeftNode(statement: AssignmentLeftNode, currentST: SymbolTable): Unit = {
    statement match {
      case lhs: IdentNode => annotateIdentNode(lhs, currentST)
      case lhs: ArrayElemNode => annotateArrayElemNode(lhs, currentST)
      case lhs: PairElemNode => annotatePairElemNode(lhs, currentST)
      //      case _: Any             => println("error"); numSemanticErrors += 1
    }
  }

  def annotateAssignmentRightNode(statement: AssignmentRightNode, currentST: SymbolTable): Unit = {
    statement match {
      case rhs: ExprNode => annotateExprNode(rhs, currentST)
      case rhs: ArrayLiteralNode => annotateArrayLiteralNode(rhs, currentST)
      case rhs: NewPairNode => annotateNewPairNode(rhs, currentST)
      case rhs: PairElemNode => annotatePairElemNode(rhs, currentST)
      case rhs: CallNode => annotateCallNode(rhs, currentST)
      case rhs: ArgListNode => annotateArgListNode(rhs, currentST)
      //      case _: Any                => println("error"); numSemanticErrors += 1
    }
  }

  def annotateNewPairNode(node: NewPairNode, currentST: SymbolTable): Unit = {
    annotateExprNode(node.fstElem, currentST)
    annotateExprNode(node.sndElem, currentST)
  }

  def annotateCallNode(call: CallNode, currentST: SymbolTable): Unit = {

    val identifier = call.id
    identifier.identType = Some(FunctionTable.getReturnType(identifier))

    call.argList match {
      case None =>
      case Some(args) => annotateArgListNode(args, currentST)
    }

    call.scopeSizes += FunctionTable.getNoOfLocalVars(identifier)
    call.symbols = MutableList(FunctionTable.getLocalVars(identifier))
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
      case expr: IdentNode => annotateIdentNode(expr, currentScopeSymbolTable)
      case expr: ArrayElemNode => annotateArrayElemNode(expr, currentScopeSymbolTable)
      case expr: UnaryOperationNode => annotateUnaryOperationNode(expr, currentScopeSymbolTable)
      case expr: BinaryOperationNode => annotateBinaryOperationNode(expr, currentScopeSymbolTable)
      case _: IntLiteralNode
           | _: BoolLiteralNode
           | _: CharLiteralNode
           | _: StringLiteralNode
           | _: PairLiteralNode =>
      //      case _: Any                    => println("error"); numSemanticErrors += 1
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
    val identType = currentST.lookupAll(identifier)
    if (identType == ErrorTypeNode()) {
      val varName = identifier.name
      SemanticErrorLog.add(s"Variable $varName doesn't exist")
    } else {
      identifier.identType = Some(currentST.lookupAll(identifier))
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
  //
  //  def getType[T](obj: T): String = {
  //    val str: String = obj.getClass.toString
  //    str.substring(6, str.length)
  //  }

}
