import org.antlr.v4.runtime.ANTLRFileStream._
import org.antlr.v4.runtime.CommonTokenStream._
import org.antlr.v4.runtime.Token._
import org.antlr.v4.runtime.tree.ParseTree._

object Main {

  def main(args : Array[String]): Unit = {

    var filename = "wacc_examples/valid/if/if1.wacc"

    var waccLex = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

    // Get a list of matched tokens
    var tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLex)

    val tokenIDs : Array[String] =  waccLex.getRuleNames()

    var waccParse = new WaccParser(tokens)

    var tree = waccParse.prog();

    for (i <- 0 until tokens.size()) {
      println("token " + i  + " is " + tokens.get(i).getText() + " of type " + mapToId(tokens.get(i).getType(), tokenIDs))
    }

    println("==================================================")

    println(tree.toStringTree(waccParse));

    println("==================================================")

    buildTree(tree, 0)

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
