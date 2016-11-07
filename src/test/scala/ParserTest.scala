import org.scalatest._

class ParserTest extends FlatSpec {

  val filepath: String = "wacc_examples/valid/basic/exit/exit-1.wacc"

  "The parseTree for exit-1" should "be" in {

    val test1 = new LexerParserTemplate(filepath)

    val result = test1.getParserResultAsTree

    val expected = "insert result ehre"

    assert(result == expected)

  }


}
