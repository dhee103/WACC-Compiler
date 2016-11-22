object TypeChecker {

  def checkDeclaration(declarationNode: DeclarationNode): (Boolean, String) = {
    val ident = declarationNode.identifier
    val rhs = declarationNode.rhs

    if (!isArrayLiteralValid(rhs)) {
      return (false, "[Semantic Error] Array literal contains expressions of differing types.")
    }

    val identType: TypeNode = ident.getType
    val rhsType: TypeNode = rhs.getType

    (rhsType.isEquivalentTo(identType), "[Semantic Error] Type mismatch in declaration statement.")
  }

  def checkAssignment(assignmentNode: AssignmentNode): (Boolean, String) = {
    val lhs = assignmentNode.lhs
    val rhs = assignmentNode.rhs

    if (!isArrayLiteralValid(rhs)) {
      return (false, "[Semantic Error] Array literal contains expressions of differing types.")
    }

    val lhsType: TypeNode = lhs.getType
    val rhsType: TypeNode = rhs.getType

    (rhsType.isEquivalentTo(lhsType), "[Semantic Error] Type mismatch in assignment statement.")
    }
  }

  // isArrayLiteralValid is true iff all expressions in array literal on rhs
  // have same type.
  // An empty array literal is always valid.
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
