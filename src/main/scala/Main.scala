

object Main {

  def main(args : Array[String]): Unit = {
    if (!args.isEmpty) sys.exit(compile(args(1)))
    else compile("hello")
  }

  def compile(in: String): Int = {
    val filename = in

    val waccLexer = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

    // Get a list of matched tokens
    val tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLexer)

    val tokenIDs : Array[String] =  waccLexer.getRuleNames()

    val waccParser = new WaccParser(tokens)

    val tree = waccParser.prog()

//    for (i <- 0 until tokens.size()) {
//      println("token " + i  + " is " + tokens.get(i).getText() + " of type " + mapToId(tokens.get(i).getType(), tokenIDs))
//    }
//
//    println("==================================================")
//
//    println(tree.toStringTree(waccParser));
//
//    println("==================================================")

    val visitor = new WaccParserDummyVisitor()

    visitor.visit(tree)

    val noErrs = waccParser.getNumberOfSyntaxErrors

    if (noErrs > 0) {
      return 100
    }

    return 0

//    sys.exit
  }

  def buildTree(currentTree: org.antlr.v4.runtime.tree.ParseTree, count: Int): Unit = {

    println(currentTree)

    for(i <- 0 to currentTree.getChildCount() - 1){
      print("\t" * count)
      print("child " + (i + 1) + " is ")
      buildTree(currentTree.getChild(i), count + 1)
    }

  }

  def mapToId(typeNum: Int, tokenNames: Array[String]): String = {
    if (typeNum > 0) tokenNames(typeNum - 1) else "EOF"
  }

}
