import org.antlr.v4.runtime.ANTLRFileStream._
import org.antlr.v4.runtime.CommonTokenStream._
import org.antlr.v4.runtime.Token._
import org.antlr.v4.runtime.tree.ParseTree._

object Main {

  def main(args : Array[String]): Unit = {

    val filename = "wacc_examples/valid/if/if1.wacc"

    val waccLexer = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

    // Get a list of matched tokens
    val tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLexer)

    val tokenIDs : Array[String] =  waccLexer.getRuleNames()

    val waccParser = new WaccParser(tokens)

    val tree = waccParser.prog();

    for (i <- 0 until tokens.size()) {
      println("token " + i  + " is " + tokens.get(i).getText() + " of type " + mapToId(tokens.get(i).getType(), tokenIDs))
    }

    println("==================================================")

    println(tree.toStringTree(waccParser));

    println("==================================================")

    val visitor = new WaccParserDummyVisitor()

    visitor.visit(tree)





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
