// import errorLogging.ErrorLog._


/*
  For testing purposes we have split the compilation into two functions.
  Compile returns the exit code as an integer as this can be tested.
*/
import java.io._

object Main2 {

  var outputString: String = null


  def main(args: Array[String]): Unit = {

    if (!args.isEmpty) {
      val input = args(0)
      val file =  input.substring(input.lastIndexOf('/') + 1, input.lastIndexOf('.'))
      val exitCode = compile(input)
      val pw = new PrintWriter(new File(s"$file.s" ))
      pw.write(outputString)
      pw.close
      println(outputString)
      sys.exit(exitCode)
    }
    else {
      sys.error("No filename passed")
    }
  }

  //    should check https://www.safaribooksonline.com/library/view/scala-cookbook/9781449340292/ch12s03.html

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

    val numSyntaxErrs = waccParser.getNumberOfSyntaxErrors
    // println(s"there are $numSyntaxErrs syntax errors")

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

    outputString = InstructionConverter.translate(CodeGen.generateProgramCode(ast))

    return 0
  }
}
