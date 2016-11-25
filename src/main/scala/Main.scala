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

    // sort out this try catch - it returns 200 far more than it should; maybe there are other errors and cases to catch
    // add methods to get number of syntax errors/ semantic errors from visitor

    // println("before visiting the tree")
    val ast: ProgNode = visitor.visit(tree).asInstanceOf[ProgNode]
    if (SyntaxErrorLog.getNumErrors > 0) {
      SyntaxErrorLog.printErrors()
      return 100
    }
    // println("visited the tree")
    Annotate.annotateAST(ast)
    if (SyntaxErrorLog.getNumErrors > 0) {
      SyntaxErrorLog.printErrors()
      return 100
    }
    if (SemanticErrorLog.getNumErrors > 0) {
      SemanticErrorLog.printErrors()
      return 200
    }
    // println("match error")
    TypeChecker.beginSemanticCheck(ast)
    // println(s"there are $numSemanticErrors semantic errors")
    if (SemanticErrorLog.getNumErrors > 0) {
      SemanticErrorLog.printErrors()
      return 200
    }

    return 0
  }
}
