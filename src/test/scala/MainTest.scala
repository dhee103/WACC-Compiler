import org.scalatest._
//TODO: be more selective with the imports
//TODO: check if matchers works at home

class MainTest extends FlatSpec with Matchers{

  "lexing wacc files" should "give the correct tokens" in {
    val files: Array[String] = Array("WaccTestFiles/basicSeq.wacc")
    val expectedTokens: Array[Array[String]] = Array(Array("BEGIN", "SKIP",
      "SEMICOLON", "SKIP", "END","EOF"))

    (files zip expectedTokens).map{ case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)}
  }

  "lexing 'beginning'" should "give us the token ID not BEGIN" in {
    val file = "WaccTestFiles/beginTest.wacc"
    val expectedTokens: Array[String] = Array("ID", "SKIP", "SEMICOLON",
      "SKIP", "END","EOF")

      assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }

  "lexing '1begin'" should "throw error" in {
    val file = "WaccTestFiles/startWithNumberTest.wacc"
    val expectedTokens: Array[String] = Array("BEGIN", "SKIP", "END","EOF")

    var flag = 0
    try {
        println(new LexerParserTemplate(file).getLexerResult ===
          expectedTokens)
      //      flag = 0
    } catch {
      case _: Throwable => flag = 1
    }
    assert(flag == 1)
  }


}
