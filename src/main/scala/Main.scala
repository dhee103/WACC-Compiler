import org.antlr.v4.runtime.DiagnosticErrorListener

object Main {

  def main(args : Array[String]): Unit = {

    val filename = "wacc_examples/invalid/syntaxErr/basic/Begin.wacc"

//    val filename = "wacc_examples/valid/basic/skip/skip.wacc"

    val waccLexer = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

    // Get a list of matched tokens
    val tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLexer)

    val tokenIDs : Array[String] =  waccLexer.getRuleNames()

    val waccParser = new WaccParser(tokens)

    val diagErrL = new DiagnosticErrorListener()

    val dEL = new DescriptiveErrorListener

    waccLexer.addErrorListener(diagErrL)
    waccParser.addErrorListener(diagErrL)

//    waccLexer.removeErrorListeners();
    waccLexer.addErrorListener(dEL);
//    waccParser.removeErrorListeners();
    waccParser.addErrorListener(dEL);

    val tree = waccParser.prog()

    for (i <- 0 until tokens.size()) {
      println("token " + i  + " is " + tokens.get(i).getText() + " of type " + mapToId(tokens.get(i).getType(), tokenIDs))
    }

    println("==================================================")

    println(tree.toStringTree(waccParser))

    println("==================================================")

    val visitor = new WaccParserDummyVisitor()

    visitor.visit(tree)

  }

  def buildTree(currentTree: org.antlr.v4.runtime.tree.ParseTree, count: Int): Unit = {

    println(currentTree)

    for(i <- 0 until currentTree.getChildCount){
      print("\t" * count)
      print("child " + (i + 1) + " is ")
      buildTree(currentTree.getChild(i), count + 1)
    }

  }

  def mapToId(typeNum: Int, tokenNames: Array[String]): String = {
    if (typeNum > 0) tokenNames(typeNum - 1) else "EOF"
  }

  def isError(waccParser: WaccParser): Boolean = {
    waccParser.getNumberOfSyntaxErrors > 0
  }

}
