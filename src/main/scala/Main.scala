// import errorLogging.ErrorLog._

/*
  For testing purposes we have split the compilation into two functions.
  Compile returns the exit code as an integer as this can be tested.
*/

object Main {
  def main(args: Array[String]): Unit = {

    if (!args.isEmpty) {
      sys.exit(compile(args(0)))
    }
    else {
      sys.error("No filename passed")
    }
  }

  def compile(filename: String): Int = {

    // maps string locations to the error message
    // can then print out nicely at the end
    // var semanticErrorLog = HashMap[String, String]()

    // to add you can do: semanticErrorLog :+= (the string)

    val waccLexer = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))
    val tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLexer)

    val waccParser = new WaccParser(tokens)

    // val diagErrL = new DiagnosticErrorListener()
    // waccLexer.addErrorListener(diagErrL)
    // waccParser.addErrorListener(diagErrL)

    val dEL = new DescriptiveErrorListener
    waccLexer.removeErrorListeners
    waccLexer.addErrorListener(dEL)
    waccParser.removeErrorListeners
    waccParser.addErrorListener(dEL)

    val tree = waccParser.prog()

    if (SyntaxErrorLog.getNumErrors > 0) {
      SyntaxErrorLog.printErrors()
      return 100
    }

    val visitor = new AstBuildingVisitor()

    val ast: ProgNode = visitor.visit(tree).asInstanceOf[ProgNode]
    if (SyntaxErrorLog.getNumErrors > 0) {
      SyntaxErrorLog.printErrors()
      return 100
    }

    Annotate.annotateAST(ast)
    if (SyntaxErrorLog.getNumErrors > 0) {
      SyntaxErrorLog.printErrors()
      return 100
    }
    if (SemanticErrorLog.getNumErrors > 0) {
      SemanticErrorLog.printErrors()
      return 200
    }

    TypeChecker.beginSemanticCheck(ast)
    if (SemanticErrorLog.getNumErrors > 0) {
      SemanticErrorLog.printErrors()
      return 200
    }

    return 0
  }
}
