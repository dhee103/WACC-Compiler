import org.scalatest._

class MainTest extends FlatSpec with BeforeAndAfter{

  "factorial(0)" should "return 1" in {
    val r = Main.factorial(0)
    assert(r === 1)
  }

  "tokens" should "be correct" in {

    val lex1 : LexerParsertemplate = new LexerParsertemplate("WaccTestFiles/basicSeq.wacc")

    val tokenList = lex1.getLexerResult

    val excpectedList : Array[String] = Array("BEGIN", "SKIP", "SEMICOLON", "SKIP", "END", "EOF")

    assert(tokenList === excpectedList)
  }




}
