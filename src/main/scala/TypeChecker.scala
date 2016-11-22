object TypeChecker {

  def checkDeclaration(declarationNode: DeclarationNode): (Boolean, String) = {
    val ident = declarationNode.identifier
    val rhs = declarationNode.rhs

    if (!isArrayLiteralValid(rhs)) {
      return (false, "[Semantic Error] Array literal contains expressions of differing types.")
    }

    val identType: TypeNode = ident.getType
    val rhsType: TypeNode = rhs.getType

    rhsType match {
      case ArrayTypeNode(null) => (identType.isInstanceOf[ArrayTypeNode], "[Semantic Error] Type mismatch in declaration statement.")
      case _ => (rhsType == identType, "[Semantic Error] Type mismatch in declaration statement.")
    }

  }

  // TO DO: Redo this entire function.
  def checkAssignment(assignmentNode: AssignmentNode) = {
    val lhs = assignmentNode.lhs
    val rhs = assignmentNode.rhs

    val lhsType = lhs match {
      case lhs: IdentNode => lhs.getType
      case lhs: PairElemNode => lhs.getType
      case lhs: ArrayElemNode => lhs.identifier.getType // finish this .unwrap
    }
  }

  // isArrayLiteralValid is true iff all expressions in array literal on rhs
  // have same type
  def isArrayLiteralValid(rhs: AssignmentRightNode): Boolean = {
    rhs match {
      case ArrayLiteralNode(values) =>
        if (values.nonEmpty) {
          values.forall(expr => expr.getType == values.head.getType)
        } else {
          true
        }
      case _ => true
    }
  }
}
