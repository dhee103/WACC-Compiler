import org.scalatest._
//TODO: be more selective with the imports

class MainTest extends FlatSpec with Matchers{

  "tokens" should "be correct" in {

    val lex1 : LexerParsertemplate = new LexerParsertemplate("WaccTestFiles/basicSeq.wacc")

    val tokenList = lex1.getLexerResult

    val excpectedList : Array[String] = Array("BEGIN", "SKIP", "SEMICOLON", "SKIP", "END", "EOF")

    assert(tokenList === excpectedList)
  }

  "toks" should "work" in {
    val files = Array("WaccTestFiles/basicSeq.wacc")
    val expectedTokens = Array(Array("BEGIN", "SKIP", "SEMICOLON", "SKIP",
      "END","EOF"))

    (files zip expectedTokens).map{ case (file, tokens) => assert(new LexerParsertemplate(file).getLexerResult === tokens)}

  }

}
