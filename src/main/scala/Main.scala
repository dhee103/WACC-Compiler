// import errorLogging.ErrorLog._




  /*
    For testing purposes we have split the compilation into two functions.
    Compile returns the exit code as an integer as this can be tested.
  */

  object Main {


  def main(args : Array[String]): Unit = {

    if (!args.isEmpty) {
      sys.exit(compile(args(0)))
    }
    else {
      sys.error("No filename passed")
    }
  }



  def compile(in: String): Int = {

// maps string locations to the error message
// can then print out nicely at the end
    // var semanticErrorLog = HashMap[String, String]()

    // to add you can do: semanticErrorLog :+= (the string)

    val filename = in
    val waccLexer = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))
    val tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLexer)

    val waccParser = new WaccParser(tokens)



    // val diagErrL = new DiagnosticErrorListener()


    // waccLexer.addErrorListener(diagErrL)
    // waccParser.addErrorListener(diagErrL)

    val dEL = new DescriptiveErrorListener
   waccLexer.removeErrorListeners();
    waccLexer.addErrorListener(dEL);
   waccParser.removeErrorListeners();
    waccParser.addErrorListener(dEL);

    val tree = waccParser.prog()

    val numSyntaxErrs = waccParser.getNumberOfSyntaxErrors
    // println(s"there are $numSyntaxErrs syntax errors")

    if (numSyntaxErrs > 0) {
      return 100
    }

    val visitor = new WaccParserDummyVisitor()
    val funcTable: FunctionTable = new FunctionTable()

    // sort out this try catch - it returns 200 far more than it shoud; maybe there are other errors and cases to catch
// add methods to get number of syntax errors/ semantic errors from visitor
    try {
      // println("before visiting the tree")
      val ast: ProgNode = visitor.visit(tree).asInstanceOf[ProgNode]
      // println("visited the tree")
      Annotate.annotateAST(ast)
    } catch {
      case _: NullPointerException =>
      case _: NumberFormatException => return 100
      case e : Throwable => println("Dodgy try catch"); println(e); return 200
    }
    // Only errors are semantic errors

    var numSemanticErrors: Int = 0
    // println("match error")
    numSemanticErrors += Annotate.numSemanticErrors + ErrorLog.getNumErrors
    // println(s"there are $numSemanticErrors semantic errors")
    if (numSemanticErrors > 0) {
     return 200
    }

    return 0
  }
}
