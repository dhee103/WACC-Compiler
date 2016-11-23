// import errorLogging.ErrorLog._


/*
  For testing purposes we have split the compilation into two functions.
  Compile returns the exit code as an integer as this can be tested.
*/
import java.io._

object Main2 {


  def main(args: Array[String]): Unit = {

    if (!args.isEmpty) {
      val input = args(0)
      val file =  input.substring(0, input.lastIndexOf('.'))
      val output = compile(input)
      val pw = new PrintWriter(new File(s"$file.s" ))
      pw.write(output)
      pw.close
      println(output)
    }
    else {
      sys.error("No filename passed")
    }
  }

  //    should check https://www.safaribooksonline.com/library/view/scala-cookbook/9781449340292/ch12s03.html


  def compile(in: String): String = {

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

    var ast: ProgNode = null
    // println(s"there are $numSyntaxErrs syntax errors")

    //if (numSyntaxErrs > 0) {
    //return 100
    //}

    val visitor = new WaccParserDummyVisitor()
    val funcTable: FunctionTable = new FunctionTable()

    // sort out this try catch - it returns 200 far more than it shoud; maybe there are other errors and cases to catch
    // add methods to get number of syntax errors/ semantic errors from visitor
    try {
      // println("before visiting the tree")
      ast = visitor.visit(tree).asInstanceOf[ProgNode]
      // println("visited the tree")
      Annotate.annotateAST(ast)
    } catch {
      case _: NullPointerException =>
      //case _: NumberFormatException => return 100
      //case e : Throwable => println("Dodgy try catch"); println(e); return 200
    }
    // Only errors are semantic errors

    var numSemanticErrors: Int = 0
    // println("match error")
    numSemanticErrors += Annotate.numSemanticErrors + ErrorLog.getNumErrors
    // println(s"there are $numSemanticErrors semantic errors")


    InstructionConverter.translate(CodeGen.generateProgramCode(ast))
  }
}
