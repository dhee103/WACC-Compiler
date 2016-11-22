trait AssignmentRightNode extends AstNode {
  def getType: TypeNode
}

case class NewPairNode(val fstElem: ExprNode, val sndElem: ExprNode) extends AssignmentRightNode {
  override def getType: TypeNode = {
    val fstElemType = fstElem.getType
    val sndElemType = sndElem.getType
    PairTypeNode(fstElemType.toPairElemTypeNode, sndElemType.toPairElemTypeNode)
  }
}

case class CallNode(val id: IdentNode, val argList: Option[ArgListNode]) extends AssignmentRightNode {
  override def getType: TypeNode = {
    val paramTypeList: IndexedSeq[TypeNode] = FunctionTable.getParamTypes(id)

    val argListMatchesParamList: Boolean = argList match {
      case Some(list) => list.exprs.length == paramTypeList.length &&
        ((list.exprs, paramTypeList).zipped map(_ == _)).forall(identity)
      case None => paramTypeList.isEmpty
    }

    if (argListMatchesParamList) {
      FunctionTable.getReturnType(id)
    } else {
      ErrorLog.add("[Semantic Error] Invalid parameters provided to function call.")
      ErrorTypeNode()
    }
  }
}
