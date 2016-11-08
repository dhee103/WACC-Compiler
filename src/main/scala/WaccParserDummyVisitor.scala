
class WaccParserDummyVisitor extends WaccParserBaseVisitor[Unit] {

  override def visitProg(ctx: WaccParser.ProgContext): Unit = {

    visitChildren(ctx)

  }

  override def visitStat(ctx: WaccParser.StatContext): Unit = {

    println("Visited stat")
    println(ctx.getText())
    println("Visiting children of a stat")
    visitChildren(ctx)

  }

  override def visitParam(ctx: WaccParser.ParamContext): Unit = {

    println("Visited param")
    println(ctx.getText())
    println("Visiting children of a param")
    visitChildren(ctx)



  }

  override def visitExpr(ctx: WaccParser.ExprContext): Unit = {

    println("Visited Expr")
    println(ctx.getText())
    visitChildren(ctx)



  }





}
