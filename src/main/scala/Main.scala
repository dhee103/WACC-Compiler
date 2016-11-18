

object Main {

  /*
    For testing purposes we have split the compilation into two functions.
    Compile returns the exit code as an integer as this can be tested.




  */

  def main(args : Array[String]): Unit = {
    if (!args.isEmpty) {
      sys.exit(compile(args(0)))
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
    println(s"there are $numSyntaxErrs syntax errors")

    if (numSyntaxErrs > 0) {
      return 100
    }

    val visitor = new WaccParserDummyVisitor()
    val funcTable: FunctionTable = new FunctionTable()

    // sort out this try catch - it returns 200 far more than it shoud; maybe there are other errors and cases to catch
// add methods to get number of syntax errors/ semantic errors from visitor
    try {
      println("before visiting the tree")
      val ast: ProgNode = visitor.visit(tree).asInstanceOf[ProgNode]
      println("visited the tree")
      Annotate.annotateAST(ast, funcTable)
    } catch {
      case _: NullPointerException =>
      case _: NumberFormatException => return 100
      case e : Throwable => println("Dodgy try catch"); println(e); return 200
    }
    // scala.MatchError: null
    /* nullPointerException for:
     binarySortTree.wacc,
     fibonacciFullRec.wacc,
     fibonacciRecursive.wacc,
     fixedPointRealArithmetic.wacc
     functionConditionalReturn.wacc
     mutualRecursion.wacc
     printInputTriangle.wacc
     simpleRecursion.wacc
     functionDeclaration.wacc
     functionManyArguments.wacc
     functionReturnPair.wacc
     functionSimple.wacc
     functionUpdateParameter.wacc
     incFunction.wacc
     sameArgName.wacc
     --> broken by changing 1 to 0  arrayEmpty.wacc
     --> broken by changing 1 to 0
     --> broken by changing 1 to 0
     --> broken by changing 1 to 0
     --> broken by changing 1 to 0
     --> broken by changing 1 to 0
     --> broken by changing 1 to 0
     --> broken by changing 1 to 0
     */
    // syntaxErr for minusNoWhitespaceExpr.wacc, plusNoWhitespaceExpr
    /* unknown unary op for:
        hashTable.wacc => table <-- error changed by changing 1 to 0
        array.wacc => a <-- error changed by changing 1 to 0
        arrayLength.wacc => a <-- fixed by changing 1 to 0
        boolExpr1.wacc => ((true&&false)||(true&&false)) <-- fixed by changing 1 to 0
        mathsExpr.wacc => ((true&&false)||(true&&false)) <-- fixed by changing 1 to 0
        multipleMathsOps.wacc => x <-- fixed by changing 1 to 0
        negExpr.wacc => x   <-- fixed by changing 1 to 0
        notExpr.wacc => a    <-- fixed by changing 1 to 0
        ordAndchrExpr.wacc => a <-- fixed by changing 1 to 0
        asciiTable.wacc => ' ' <-- fixed by changing 1 to 0
        negFunction.wacc => b <-- fixed by changing 1 to 0
        intnegateOverflow.wacc => x <-- fixed by changing 1 to 0
        whileBoolFlip.wacc => b x <-- fixed by changing 1 to 0
    */

    /*
    *
    *
    *
    *
    *
    *
    *
    * */

    var numSemanticErrors: Int = 0
    println("match error")
    numSemanticErrors += Annotate.getNumberOfSemanticErrors
    println(s"there are $numSemanticErrors semantic errors")
    if (numSemanticErrors > 0) {
     return 200
    }

    return 0
  }
}
