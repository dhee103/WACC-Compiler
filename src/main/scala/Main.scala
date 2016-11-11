object Main {

  def main(args : Array[String]): Unit = {
    if (!args.isEmpty) {
      sys.exit(compile(args(0)))
      // println(compile(args(0)))
    }
    else {
      sys.error("No filename passed")
    }
  }

  def compile(in: String): Int = {
    val filename = in

    val waccLexer = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

    val tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLexer)

    val waccParser = new WaccParser(tokens)

    val tree = waccParser.prog()

    val numSyntaxErrs = waccParser.getNumberOfSyntaxErrors
    if (numSyntaxErrs > 0) {
      return 100
    }


    val visitor = new WaccParserDummyVisitor()
    try {
      val ast: AstNode = visitor.visit(tree)
    } catch {
      case _:NumberFormatException => return 100
      case _:Throwable => return 200
    }


//    if (semanticErrs > 0) {
//      return 200
//    }

    return 0

  }
}
