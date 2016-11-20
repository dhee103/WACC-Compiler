object TypeChecker {

  def checkDeclaration(declarationNode: DeclarationNode): Boolean = {
    val ident = declarationNode.identifier
    val rhs = declarationNode.rhs

    // TO DO: Checking if each element of an array is of same type
    rhs match {
      case rhs: ArrayLiteralNode =>
        if (rhs.values.nonEmpty) {
        rhs.values.forall(expr => expr.getType ==
          rhs.values
          .head
          .getType)
      }
    }

    val identType: TypeNode = ident.getType
    val rhsType: TypeNode = rhs.getType

    rhsType match {
      case ArrayTypeNode(null) => identType.isInstanceOf[ArrayTypeNode]
      case _                   => rhsType == identType
    }

  }

  def checkAssignment(assignmentNode: AssignmentNode) = {
    val lhs = assignmentNode.lhs
    val rhs = assignmentNode.rhs

    val lhsType = lhs match {
      case lhs: IdentNode => lhs.getType
      case lhs: PairElemNode => lhs.getType
      case lhs: ArrayElemNode => lhs.identifier.getType // finish this .unwrap
    }
  }
}
