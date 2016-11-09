import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

class WaccParserDummyVisitor extends WaccParserBaseVisitor[AstNode] {

  override def visitProg(ctx: WaccParser.ProgContext): AstNode = {
    val noOfChildren = ctx.getChildCount
    val statChild: StatNode = visit(ctx.getChild(noOfChildren - 2)).asInstanceOf[StatNode]

    val funcChildren: IndexedSeq[FuncNode] =
      for (i <- 1 until noOfChildren - 3) yield visit(ctx.getChild(i)).asInstanceOf[FuncNode]
    new ProgNode(statChild, funcChildren)
  }

  override def visitFunc(ctx: WaccParser.FuncContext): AstNode = super.visitFunc(ctx)

  override def visitParam_list(ctx: WaccParser.Param_listContext): AstNode = super.visitParam_list(ctx)

  override def visitParam(ctx: WaccParser.ParamContext): AstNode = super.visitParam(ctx)

  override def visitAssignment(ctx: WaccParser.AssignmentContext): AstNode = super.visitAssignment(ctx)

  override def visitSkipStat(ctx: WaccParser.SkipStatContext): AstNode = super.visitSkipStat(ctx)

  override def visitRead(ctx: WaccParser.ReadContext): ReadNode = {
    val variable: AssignmentLeftNode = visit(ctx.getChild(1)).asInstanceOf[AssignmentLeftNode]

    new ReadNode(variable)
  }

  override def visitFree(ctx: WaccParser.FreeContext): FreeNode = {
    val variable: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    new FreeNode(variable)
  }

  override def visitReturn(ctx: WaccParser.ReturnContext): ReturnNode = {
    val returnValue: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    new ReturnNode(returnValue)
  }

  override def visitPrint(ctx: WaccParser.PrintContext): PrintNode = {
    val text: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    new PrintNode(text)
  }

  override def visitPrintln(ctx: WaccParser.PrintlnContext): PrintlnNode = {
    val text: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    new PrintlnNode(text)
  }

  override def visitNewBegin(ctx: WaccParser.NewBeginContext): AstNode = super.visitNewBegin(ctx)

  override def visitDeclaration(ctx: WaccParser.DeclarationContext): AstNode = super.visitDeclaration(ctx)

  override def visitWhile(ctx: WaccParser.WhileContext): AstNode = super.visitWhile(ctx)

  override def visitSequence(ctx: WaccParser.SequenceContext): AstNode = super.visitSequence(ctx)

  override def visitIf(ctx: WaccParser.IfContext): AstNode = super.visitIf(ctx)

  override def visitExit(ctx: WaccParser.ExitContext): AstNode = super.visitExit(ctx)

  override def visitIdentLHS(ctx: WaccParser.IdentLHSContext): AstNode = super.visitIdentLHS(ctx)

  override def visitArrayElemLHS(ctx: WaccParser.ArrayElemLHSContext): AstNode = super.visitArrayElemLHS(ctx)

  override def visitPairElemLHS(ctx: WaccParser.PairElemLHSContext): AstNode = super.visitPairElemLHS(ctx)

  override def visitExprL(ctx: WaccParser.ExprLContext): AstNode = super.visitExprL(ctx)

  override def visitArrayLiteral(ctx: WaccParser.ArrayLiteralContext): AstNode = super.visitArrayLiteral(ctx)

  override def visitNewPair(ctx: WaccParser.NewPairContext): AstNode = super.visitNewPair(ctx)

  override def visitPairElem(ctx: WaccParser.PairElemContext): AstNode = super.visitPairElem(ctx)

  override def visitCall(ctx: WaccParser.CallContext): AstNode = super.visitCall(ctx)

  override def visitArg_list(ctx: WaccParser.Arg_listContext): AstNode = super.visitArg_list(ctx)

  override def visitFst(ctx: WaccParser.FstContext): AstNode = super.visitFst(ctx)

  override def visitSnd(ctx: WaccParser.SndContext): AstNode = super.visitSnd(ctx)

  override def visitPairType(ctx: WaccParser.PairTypeContext): AstNode = super.visitPairType(ctx)

  override def visitBaseType(ctx: WaccParser.BaseTypeContext): AstNode = super.visitBaseType(ctx)

  override def visitTypeL(ctx: WaccParser.TypeLContext): AstNode = super.visitTypeL(ctx)

  override def visitInt(ctx: WaccParser.IntContext): AstNode = super.visitInt(ctx)

  override def visitBool(ctx: WaccParser.BoolContext): AstNode = super.visitBool(ctx)

  override def visitChar(ctx: WaccParser.CharContext): AstNode = super.visitChar(ctx)

  override def visitString(ctx: WaccParser.StringContext): AstNode = super.visitString(ctx)

  override def visitPair_type(ctx: WaccParser.Pair_typeContext): AstNode = super.visitPair_type(ctx)

  override def visitPair_elem_type(ctx: WaccParser.Pair_elem_typeContext): AstNode = super.visitPair_elem_type(ctx)

  override def visitBinaryOp4(ctx: WaccParser.BinaryOp4Context): AstNode = super.visitBinaryOp4(ctx)

  override def visitUnaryOperation(ctx: WaccParser.UnaryOperationContext): AstNode = super.visitUnaryOperation(ctx)

  override def visitBinaryOp3(ctx: WaccParser.BinaryOp3Context): AstNode = super.visitBinaryOp3(ctx)

  override def visitCharLiteral(ctx: WaccParser.CharLiteralContext): AstNode = super.visitCharLiteral(ctx)

  override def visitParens(ctx: WaccParser.ParensContext): AstNode = super.visitParens(ctx)

  override def visitPairLiteral(ctx: WaccParser.PairLiteralContext): AstNode = super.visitPairLiteral(ctx)

  override def visitLogicalOr(ctx: WaccParser.LogicalOrContext): AstNode = super.visitLogicalOr(ctx)

  override def visitBoolLiteral(ctx: WaccParser.BoolLiteralContext): AstNode = super.visitBoolLiteral(ctx)

  override def visitStringLiteral(ctx: WaccParser.StringLiteralContext): AstNode = super.visitStringLiteral(ctx)

  override def visitArrayElem(ctx: WaccParser.ArrayElemContext): AstNode = super.visitArrayElem(ctx)

  override def visitLogicalAnd(ctx: WaccParser.LogicalAndContext): AstNode = super.visitLogicalAnd(ctx)

  override def visitIntLiteral(ctx: WaccParser.IntLiteralContext): AstNode = super.visitIntLiteral(ctx)

  override def visitPlus(ctx: WaccParser.PlusContext): AstNode = super.visitPlus(ctx)

  override def visitBinaryOp1(ctx: WaccParser.BinaryOp1Context): AstNode = super.visitBinaryOp1(ctx)

  override def visitIdentL(ctx: WaccParser.IdentLContext): AstNode = super.visitIdentL(ctx)

  override def visitLogicalNot(ctx: WaccParser.LogicalNotContext): AstNode = super.visitLogicalNot(ctx)

  override def visitNegative(ctx: WaccParser.NegativeContext): AstNode = super.visitNegative(ctx)

  override def visitLen(ctx: WaccParser.LenContext): AstNode = super.visitLen(ctx)

  override def visitOrd(ctx: WaccParser.OrdContext): AstNode = super.visitOrd(ctx)

  override def visitChr(ctx: WaccParser.ChrContext): AstNode = super.visitChr(ctx)

  override def visitMulOperation(ctx: WaccParser.MulOperationContext): AstNode = super.visitMulOperation(ctx)

  override def visitDivOperation(ctx: WaccParser.DivOperationContext): AstNode = super.visitDivOperation(ctx)

  override def visitMod(ctx: WaccParser.ModContext): AstNode = super.visitMod(ctx)

  override def visitGreaterThan(ctx: WaccParser.GreaterThanContext): AstNode = super.visitGreaterThan(ctx)

  override def visitGreaterEqual(ctx: WaccParser.GreaterEqualContext): AstNode = super.visitGreaterEqual(ctx)

  override def visitLessThan(ctx: WaccParser.LessThanContext): AstNode = super.visitLessThan(ctx)

  override def visitLessEqual(ctx: WaccParser.LessEqualContext): AstNode = super.visitLessEqual(ctx)

  override def visitDoubleEqual(ctx: WaccParser.DoubleEqualContext): AstNode = super.visitDoubleEqual(ctx)

  override def visitNotEqual(ctx: WaccParser.NotEqualContext): AstNode = super.visitNotEqual(ctx)

  override def visitIdent(ctx: WaccParser.IdentContext): AstNode = super.visitIdent(ctx)

  override def visitArray_elem(ctx: WaccParser.Array_elemContext): AstNode = super.visitArray_elem(ctx)

  override def visitInt_liter(ctx: WaccParser.Int_literContext): AstNode = super.visitInt_liter(ctx)

  override def visitBool_liter(ctx: WaccParser.Bool_literContext): AstNode = super.visitBool_liter(ctx)

  override def visitChar_liter(ctx: WaccParser.Char_literContext): AstNode = super.visitChar_liter(ctx)

  override def visitStr_liter(ctx: WaccParser.Str_literContext): AstNode = super.visitStr_liter(ctx)

  override def visitArray_liter(ctx: WaccParser.Array_literContext): AstNode = super.visitArray_liter(ctx)

  override def visitPair_liter(ctx: WaccParser.Pair_literContext): AstNode = {
    return super.visitPair_liter(ctx)
  }

}
