import java.io.{File, PrintWriter}

/*
  For testing purposes we have split the compilation into two functions.
  Compile returns the exit code as an integer as this can be tested.
*/

object Main {

  //  def time[R](block: => R): R = {
  //    val t0 = System.nanoTime()
  //    val result = block    // call-by-name
  //    val t1 = System.nanoTime()
  //    println("Elapsed time: " + (t1 - t0) + "ns")
  //    result
  //  }

  def time(f: => Unit) = {
    val s = System.currentTimeMillis
    f
    System.currentTimeMillis() - s
//    println(System.currentTimeMillis() - s)
  }

  var outputString: String = ""

  def main(args: Array[String]): Unit = {

    time {

      if (!args.isEmpty) {
        val input = args(0)
        val file = input.substring(input.lastIndexOf('/') + 1, input
          .lastIndexOf('.'))

        val exitCode = compile(input)
        val pw = new PrintWriter(new File(s"$file.s"))
        pw.write(outputString)
        pw.close
//        println(s"exitcode: $exitCode")
                sys.exit(exitCode)
      }
      else {
        sys.error("No filename passed")
      }

    }
  }

  def compile(filename: String): Int = {

    // maps string locations to the error message
    // can then print out nicely at the end
    // var semanticErrorLog = HashMap[String, String]()

    // to add you can do: semanticErrorLog :+= (the string)

    val waccLexer = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream
    (filename))
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

    if (SyntaxErrorLog.numErrors > 0) {
      SyntaxErrorLog.printErrors()
      return 100
    }

    val visitor = new AstBuildingVisitor()

    val ast: ProgNode = visitor.visit(tree).asInstanceOf[ProgNode]
    if (SyntaxErrorLog.numErrors > 0) {
      SyntaxErrorLog.printErrors()
      return 100
    }

    Annotate.annotateAST(ast)
    if (SyntaxErrorLog.numErrors > 0) {
      SyntaxErrorLog.printErrors()
      return 100
    }
    if (SemanticErrorLog.numErrors > 0) {
      SemanticErrorLog.printErrors()
      return 200
    }

    TypeChecker.beginSemanticCheck(ast)
    if (SemanticErrorLog.numErrors > 0) {
      SemanticErrorLog.printErrors()
      return 200
    }

    outputString = InstructionConverter.translate(CodeGen.generateProgramCode(ast))

    return 0
  }


}

//wacc_examples/invalid/syntaxErr/basic/Begin.wacc

//wacc_examples/valid/basic/skip/skip.wacc