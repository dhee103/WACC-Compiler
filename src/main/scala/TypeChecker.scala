object TypeChecker {

  def beginSemanticCheck(node: ProgNode): Unit = {
    // check all funcNodes in funcChildren
    checkStatement(node.statChild)
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
      case stat: IfNode                    => checkIf(stat)
      case stat: WhileNode                 => checkWhile(stat)
      case NewBeginNode(stat)              => checkStatement(stat)
      case stat: SkipStatNode              =>
    }
  }

  def checkDeclaration(declarationNode: DeclarationNode): Unit = {
    val ident = declarationNode.identifier
    val rhs = declarationNode.rhs

    if (!isArrayLiteralValid(rhs)) {
      SemanticErrorLog.add("[Semantic Error] Array literal in declaration contains expressions of differing types.")
      return
    }

    val identType: TypeNode = ident.getType
    val rhsType: TypeNode = rhs.getType

    if (!rhsType.isEquivalentTo(identType)) {
      SemanticErrorLog.add("[Semantic Error] Type mismatch in declaration statement.")
    }
  }

  def checkAssignment(assignmentNode: AssignmentNode): Unit = {
    val lhs = assignmentNode.lhs
    val rhs = assignmentNode.rhs

    if (!isArrayLiteralValid(rhs)) {
      SemanticErrorLog.add("[Semantic Error] Array literal in assignment contains expressions of differing types.")
      return
    }

    val lhsType: TypeNode = lhs.getType
    val rhsType: TypeNode = rhs.getType

    if (!rhsType.isEquivalentTo(lhsType)) {
      SemanticErrorLog.add("[Semantic Error] Type mismatch in assignment statement.")
    }

  }

  def checkRead(readNode: ReadNode): Unit = {
    val target = readNode.variable
    val targetType = target.getType
    val targetIsChar = targetType.isEquivalentTo(CharTypeNode())
    val targetIsInt = targetType.isEquivalentTo(IntTypeNode())

    if (!(targetIsInt || targetIsChar)) {
      SemanticErrorLog.add("[Semantic Error] Read statement expects integer or character target.")
    }
  }

  def checkFree(freeNode: FreeNode): Unit = {
    val variable = freeNode.variable
    val varType = variable.getType
    val varIsPair = varType.isInstanceOf[PairTypeNode]
    val varIsArray = varType.isInstanceOf[ArrayTypeNode]

    if (!(varIsPair || varIsArray)) {
      SemanticErrorLog.add("[Semantic Error] Free statement expects pair or array target.")
    }
  }

  def checkExit(exitNode: ExitNode): Unit = {
    val exitCode = exitNode.exitCode
    val exitCodeType = exitCode.getType
    val exitCodeIsInt = exitCodeType.isEquivalentTo(IntTypeNode())

    if (!exitCodeIsInt) {
      SemanticErrorLog.add("[Semantic Error] Exit statement expects integer argument.")
    }
  }

  def checkIf(ifNode: IfNode): Unit = {
    checkStatement(ifNode.thenStat)
    checkStatement(ifNode.elseStat)

    val condType = ifNode.condition.getType
    val condIsBoolean = condType.isEquivalentTo(BoolTypeNode())
    if (!condIsBoolean) {
      SemanticErrorLog.add("[Semantic Error] If statement expects boolean condition.")
    }
  }

  def checkWhile(whileNode: WhileNode): Unit = {
    checkStatement(whileNode.loopBody)

    val condType = whileNode.condition.getType
    val condIsBoolean = condType.isEquivalentTo(BoolTypeNode())
    if (!condIsBoolean) {
      SemanticErrorLog.add("[Semantic Error] While statement expects boolean condition.")
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
