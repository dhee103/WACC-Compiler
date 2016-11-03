import org.scalatest._
//TODO: be more selective with the imports
//TODO: check if matchers works at home

class MainTest extends FlatSpec with Matchers{

  "toks" should "work" in {
    val files: Array[String] = Array("WaccTestFiles/basicSeq.wacc")
    val expectedTokens: Array[Array[String]] = Array(Array("BEGIN", "SKIP",
      "SEMICOLON", "SKIP", "END","EOF"))

    (files zip expectedTokens).map{ case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)}
  }

}
