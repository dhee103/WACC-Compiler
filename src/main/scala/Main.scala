import org.antlr.v4.runtime.ANTLRFileStream._
import org.antlr.v4.runtime.CommonTokenStream._
import org.antlr.v4.runtime.Token._
import org.antlr.v4.runtime.tree.ParseTree._

object Main {

  def main(args : Array[String]): Unit = {

    var filename = "wacc_examples/valid/expressions/boolCalc.wacc"

    var waccLex = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

    // Get a list of matched tokens
    var tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLex)

    val tokenIDs : Array[String] =  waccLex.getRuleNames()

    var waccParse = new WaccParser(tokens)

    val parseVocab = waccParse.getVocabulary()

    var tree = waccParse.prog();

    for (i <- 0 until tokens.size()) {
      println("token " + i  + " is " + tokens.get(i).getText() + " of type " + mapToId(tokens.get(i).getType(), tokenIDs))
    }

    println("==================================================")

    println(tree.toStringTree(waccParse));

    println("==================================================")

    mad(tree)

  }

  def mad(ting: org.antlr.v4.runtime.tree.ParseTree): Unit = {

    println(parseVocab.getSymbolicName(ting.getPayload().getType()))

  }

  def mapToId(typeNum: Int, tokenNames: Array[String]): String = {
    if (typeNum > 0) tokenNames(typeNum - 1) else "EOF"
  }

}
