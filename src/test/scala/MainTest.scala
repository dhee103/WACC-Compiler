import org.scalatest._
import org.antlr.v4.runtime.ANTLRFileStream._
import org.antlr.v4.runtime.CommonTokenStream._
import org.antlr.v4.runtime.Token._


class MainTest extends FlatSpec with BeforeAndAfter{

    var filename = "WaccTestFiles/basicSeq.wacc"

    var waccLex = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

    // Get a list of matched tokens
    var tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLex)

    val tokenIDs : Array[String] =  waccLex.getRuleNames()

    println(tokens.getText)

  def mapToId(typeNum: Int, tokenNames: Array[String]): String = {
    if (typeNum > 0) tokenNames(typeNum - 1) else "EOF"
  }

  def constructTypeNumberArrays(xs : org.antlr.v4.runtime.CommonTokenStream): Array[Int] = {

    var types : Array[Int] = new Array[Int](xs.size())

    for(i <- 0 to (types.length - 1)){

      types(i) = tokens.get(i).getType()

    }

    return types

  }

  def ConstructTokenStringArray(xs : Array[Int]): Array[String] = {

      return xs.map(mapToId(_, tokenIDs))
  }

  "factorial(0)" should "return 1" in {
    val r = Main.factorial(0)
    assert(r === 1)
  }

  "tokens" should "be correct" in {

    val typeList = constructTypeNumberArrays(tokens)

    val tokenList = ConstructTokenStringArray(typeList)

    val excpectedList : Array[String] = Array("BEGIN", "SKIP", "SEMICOLON", "SKIP", "END", "EOF")

    assert(tokenList === excpectedList)
  }




}
