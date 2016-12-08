object TypeChecker {

  def beginSemanticCheck(node: ProgNode): Unit = {
    // check all funcNodes in funcChildren
    for (func <- node.funcChildren) {
      checkStatement(func.statement)
      checkFunctionReturnStatement(func.statement, func.returnType, func.identifier.name)
    }

    checkStatement(node.statChild)
  }

  def checkFunctionReturnStatement(statement: StatNode, correctType: TypeNode, functionName: String): Unit = {
    statement match {
      case ReturnNode(expr) => val foundType = expr.getType
        if (!foundType.isEquivalentTo(correctType))
        SemanticErrorLog.add(s"Function $functionName expected return type $correctType. Found $foundType.")
      case stat: SequenceNode => checkFunctionReturnStatement(stat.sndStat, correctType, functionName)
      case stat: IfThenElseNode => checkFunctionReturnStatement(stat.thenStat, correctType, functionName)
        checkFunctionReturnStatement(stat.elseStat, correctType, functionName)
//      case stat: IfThenNode => checkFunctionReturnStatement(stat.thenStat, correctType, functionName)
      case stat: IfElifNode =>
        checkFunctionReturnStatement(stat.thenStat, correctType, functionName)
        for (thenStat <- stat.elifStats) {
          checkFunctionReturnStatement(thenStat, correctType, functionName)
        }
        if (stat.elseStat.nonEmpty) {
          checkFunctionReturnStatement(stat.elseStat.get, correctType, functionName)
        }

      case stat: NewBeginNode => checkFunctionReturnStatement(stat.body, correctType, functionName)
      case _ =>
    }
  }

  def checkStatement(statement: StatNode): Unit = {
    statement match {
      case SequenceNode(fstStat, sndStat)  => checkStatement(fstStat)
                                              checkStatement(sndStat)
      case stat: DeclarationNode           => checkDeclaration(stat)
      case stat: AssignmentNode            => checkAssignment(stat)
      case stat: ReadNode                  => checkRead(stat)
      case stat: FreeNode                  => checkFree(stat)
      case ReturnNode(expr)                => expr.getType
      case stat: ExitNode                  => checkExit(stat)
      case PrintNode(expr)                 => expr.getType
      case PrintlnNode(expr)               => expr.getType
      case stat: IfThenElseNode            => checkIf(stat)
//      case stat: IfThenNode                => checkIfExt(stat)
      case stat: IfElifNode                => checkIfElif(stat)
      case stat: WhileNode                 => checkWhile(stat)
      case NewBeginNode(stat)              => checkStatement(stat)
      case stat: SkipStatNode              => // nothing needs to be done
      case stat: BreakNode                 => // nothing needs to be done
//      case s => println(s"wtf is: $s")
//        TODO: Decide whether we need to check the location of a break i.e. can it only be used inside a scope extender?
    }
  }

  def checkDeclaration(declarationNode: DeclarationNode): Unit = {
    val ident = declarationNode.identifier
    val rhs = declarationNode.rhs

    if (!isArrayLiteralValid(rhs)) {
      SemanticErrorLog.add("Array literal in declaration contains expressions of differing types.")
      return
    }

    val identType: TypeNode = ident.getType
    val rhsType: TypeNode = rhs.getType

    if (!rhsType.isEquivalentTo(identType)) {
      SemanticErrorLog.add(s"Type mismatch in declaration statement. Expected: $identType. Actual: $rhsType.")
    }
  }

  def checkAssignment(assignmentNode: AssignmentNode): Unit = {
    val lhs = assignmentNode.lhs
    val rhs = assignmentNode.rhs

    if (!isArrayLiteralValid(rhs)) {
      SemanticErrorLog.add("Array literal in assignment contains expressions of differing types.")
      return
    }

    val lhsType: TypeNode = lhs.getType
    val rhsType: TypeNode = rhs.getType

    if (!rhsType.isEquivalentTo(lhsType)) {
      SemanticErrorLog.add("Type mismatch in assignment statement.")
    }

  }

  def checkRead(readNode: ReadNode): Unit = {
    val target = readNode.variable
    val targetType = target.getType
    val targetIsChar = targetType.isEquivalentTo(CharTypeNode())
    val targetIsInt = targetType.isEquivalentTo(IntTypeNode())

    if (!(targetIsInt || targetIsChar)) {
      SemanticErrorLog.add("Read statement expects integer or character target.")
    }
  }

  def checkFree(freeNode: FreeNode): Unit = {
    val variable = freeNode.variable
    val varType = variable.getType
    val varIsPair = varType.isInstanceOf[PairTypeNode]
    val varIsArray = varType.isInstanceOf[ArrayTypeNode]

    if (!(varIsPair || varIsArray)) {
      SemanticErrorLog.add("Free statement expects pair or array target.")
    }
  }

  def checkExit(exitNode: ExitNode): Unit = {
    val exitCode = exitNode.exitCode
    val exitCodeType = exitCode.getType
    val exitCodeIsInt = exitCodeType.isEquivalentTo(IntTypeNode())

    if (!exitCodeIsInt) {
      SemanticErrorLog.add("Exit statement expects integer argument.")
    }
  }

  def checkIf(ifNode: IfThenElseNode): Unit = {
    checkStatement(ifNode.thenStat)
    checkStatement(ifNode.elseStat)

    val condType = ifNode.condition.getType
    val condIsBoolean = condType.isEquivalentTo(BoolTypeNode())
    if (!condIsBoolean) {
      SemanticErrorLog.add("If statement expects boolean condition.")
    }
  }

//  def checkIfExt(ifNode: IfThenNode): Unit = {
//    checkStatement(ifNode.thenStat)
////    checkStatement(ifNode.elseStat)
//
//    val condType = ifNode.condition.getType
//    val condIsBoolean = condType.isEquivalentTo(BoolTypeNode())
//    if (!condIsBoolean) {
//      SemanticErrorLog.add("If statement expects boolean condition.")
//    }
//  }

  def checkIfElif(ifNode: IfElifNode): Unit = {
    if (ifNode.elseStat.isDefined) {
      checkStatement(ifNode.elseStat.get)
    }

    //    check condition and check Statement for the if and for each elif
    for ((cond, stat) <- ifNode.condition :: ifNode.elifConds zip ifNode.thenStat :: ifNode.elifStats) {
      checkStatement(stat)
      val condType = cond.getType
      val condIsBoolean = condType.isEquivalentTo(BoolTypeNode())
      if (!condIsBoolean) {
        SemanticErrorLog.add("If statement expects boolean condition.")
      }
    }
  }


  def checkWhile(whileNode: WhileNode): Unit = {
    checkStatement(whileNode.loopBody)

    val condType = whileNode.condition.getType
    val condIsBoolean = condType.isEquivalentTo(BoolTypeNode())
    if (!condIsBoolean) {
      SemanticErrorLog.add("While statement expects boolean condition.")
    }
  }

  // isArrayLiteralValid is true iff all expressions in array literal on rhs
  // have same type.
  // An empty array literal is always valid.
  def isArrayLiteralValid(rhs: AssignmentRightNode): Boolean = {
    rhs match {
      case ArrayLiteralNode(values) =>
        if (values.nonEmpty) {
          values.forall(expr => expr.getType.isEquivalentTo(values.head.getType))
        } else {
          true
        }
      case _ => true
    }
  }
}
