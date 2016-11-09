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

  override def visitFunc(ctx: WaccParser.FuncContext): AstNode = null

  override def visitParam_list(ctx: WaccParser.Param_listContext): AstNode = null

  override def visitParam(ctx: WaccParser.ParamContext): AstNode = null

  override def visitStat(ctx: WaccParser.StatContext): StatNode = null

  override def visitAssign_lhs(ctx: WaccParser.Assign_lhsContext): AstNode = null

  override def visitAssign_rhs(ctx: WaccParser.Assign_rhsContext): AstNode = null

  override def visitArg_list(ctx: WaccParser.Arg_listContext): AstNode = null

  override def visitPair_elem(ctx: WaccParser.Pair_elemContext): AstNode = null

  override def visitType(ctx: WaccParser.TypeContext): AstNode = null

  override def visitBase_type(ctx: WaccParser.Base_typeContext): AstNode = null

  override def visitPair_type(ctx: WaccParser.Pair_typeContext): AstNode = null

  override def visitPair_elem_type(ctx: WaccParser.Pair_elem_typeContext): AstNode = null

  override def visitExpr(ctx: WaccParser.ExprContext): AstNode = null

  override def visitUnary_oper(ctx: WaccParser.Unary_operContext): AstNode = null

  override def visitIdent(ctx: WaccParser.IdentContext): AstNode = null

  override def visitArray_elem(ctx: WaccParser.Array_elemContext): AstNode = null

  override def visitInt_liter(ctx: WaccParser.Int_literContext): AstNode = null

  override def visitBool_liter(ctx: WaccParser.Bool_literContext): AstNode = null

  override def visitChar_liter(ctx: WaccParser.Char_literContext): AstNode = null

  override def visitStr_liter(ctx: WaccParser.Str_literContext): AstNode = null

  override def visitArray_liter(ctx: WaccParser.Array_literContext): AstNode = null

  override def visitPair_liter(ctx: WaccParser.Pair_literContext): AstNode = null

  override def visit(parseTree: ParseTree): AstNode = null

  override def visitChildren(ruleNode: RuleNode): AstNode = null

  override def visitTerminal(terminalNode: TerminalNode): AstNode = null

  override def visitErrorNode(errorNode: ErrorNode): AstNode = null

}
