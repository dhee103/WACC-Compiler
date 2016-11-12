object Main {

  def main(args : Array[String]): Unit = {
//    println("started main")
    if (!args.isEmpty) {
//      println("args not empty")
      sys.exit(compile(args(0)))
//      // println(compile(args(0)))
    }
    else {
//      println("no file")
      sys.error("No filename passed")
    }
  }

  def compile(in: String): Int = {

//    println("started compile")
    val filename = in

//    println("got file from in")

    val waccLexer = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

//    println("generated lexer")

    val tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLexer)

//    println("generated tokens")

    val waccParser = new WaccParser(tokens)

//    println("generated parser")

    val tree = waccParser.prog()

//    println("generated parseTree")

    val numSyntaxErrs = waccParser.getNumberOfSyntaxErrors

//    println("found number of syntax errors")

    if (numSyntaxErrs > 0) {

//      println("More than 0 syntax errors")

      return 100
    }

//    println("About to generate visitor")

    val visitor = new WaccParserDummyVisitor()

//    println("generated visitor")

    try {
//      println("started try")
      val ast: AstNode = visitor.visit(tree)
//      println("generated ast")
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
