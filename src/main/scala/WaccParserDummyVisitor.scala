class WaccParserDummyVisitor extends WaccParserBaseVisitor[Unit] {

  override def visitProg(ctx: WaccParser.ProgContext): Unit = {

    visitChildren(ctx)

  }

  override def visitStat(ctx: WaccParser.StatContext): Unit = {

    println("Visited stat")
    println(ctx.getText())
    println()

    visitChildren(ctx)

  }

  override def visitProg(ctx: WaccParser.ProgContext): Unit = {

    println("beans")
    visitChildren(ctx)
    println("on toast")

  }

}
