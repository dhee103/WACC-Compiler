

object Main {

  def main(args : Array[String]): Unit = {
    if (!args.isEmpty) {
      sys.exit(compile(args(1)))
    }
    else {
      sys.error("No filename passed")
    }
  }

  def compile(in: String): Int = {
    val filename = in

    val waccLexer = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

    // Get a list of matched tokens
    val tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLexer)

//    val tokenIDs : Array[String] =  waccLexer.getRuleNames

    val waccParser = new WaccParser(tokens)

    val tree = waccParser.prog()

    val numSyntaxErrs = waccParser.getNumberOfSyntaxErrors
    if (numSyntaxErrs > 0) {
      return 100
    }


    val visitor = new WaccParserDummyVisitor()
    visitor.visit(tree)

//    if (semanticErrs > 0) {
//      return 200
//    }

    return 0

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

}
