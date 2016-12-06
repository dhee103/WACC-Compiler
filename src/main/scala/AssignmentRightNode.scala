trait AssignmentRightNode extends AstNode {
  def getType: TypeNode
}

case class NewPairNode(fstElem: ExprNode, sndElem: ExprNode) extends AssignmentRightNode {
  override def getType: TypeNode = {
    val fstElemType = fstElem.getType
    val sndElemType = sndElem.getType
    PairTypeNode(fstElemType.toPairElemTypeNode, sndElemType.toPairElemTypeNode)
  }
}

case class CallNode(id: IdentNode, argList: Option[ArgListNode]) extends AssignmentRightNode with ScopeExtender {
  override def getType: TypeNode = {
    val paramTypeList: List[TypeNode] = FunctionTable.getParamTypes(id)

    val argListMatchesParamList: Boolean = argList match {
      case Some(list) => list.exprs.length == paramTypeList.length &&
        (list.exprs, paramTypeList).zipped.map(_.getType.isEquivalentTo(_)).forall(identity)

      case None => paramTypeList.isEmpty
    }

    if (argListMatchesParamList) {
      FunctionTable.getReturnType(id)
    } else {
      SemanticErrorLog.add("Invalid parameters provided to function call.")
      ErrorTypeNode()
    }
  }

  def params: List[IdentNode] = FunctionTable.getParamIdents(id)

  def args: IndexedSeq[ExprNode] = {
    argList match {
      case None => IndexedSeq[ExprNode]()
      case Some(list) => list.exprs
    }
  }

  def functionBody: StatNode = FunctionTable.getBody(id)
}
