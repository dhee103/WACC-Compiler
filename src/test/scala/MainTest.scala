import org.scalatest._
//TODO: be more selective with the imports
//TODO: check if matchers works at home

class MainTest extends FlatSpec with Matchers{

  val valid: String = "wacc_examples/valid/"
  val synatxErr: String = "wacc_examples/invalid/syntaxErr/"



//  The below tests are all failing tests and are more specialised
//  failing test
  "lexing 'beginning'" should "give us the token ID not BEGIN" in {
    val file = synatxErr + "basic/beginning.wacc"
    val expectedTokens: Array[String] = Array("ID", "SKIP", "SEMICOLON",
      "SKIP", "END","EOF")
    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }
//
////  failing test
////  TODO: find out why this isn't throwing an error
//  "lexing '1begin'" should "throw error" in {
//    val file = synatxErr + "statements/startWithNumberTest.wacc"
//    val expectedTokens: Array[String] = Array("BEGIN", "SKIP", "END","EOF")
////    println(new LexerParserTemplate(file).getLexerResult.mkString(" "))
////    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
//    try {
//      new LexerParserTemplate(file).getLexerResult === expectedTokens
//      fail("This test should fail")
//    } catch {
//      case _: Throwable => succeed
//    }
//  }
//
////  passing test
//  "lexing with no semicolons" should "work" in {
//    val file = synatxErr + "statements/noSemicolons.wacc"
//    val expectedTokens: Array[String]
//      = Array("BEGIN", "SKIP", "SKIP", "END", "EOF")
//
////    var flag = 0
////    try {
////      new LexerParserTemplate(file).getLexerResult === expectedTokens
////      //      flag = 0
////    } catch {
////      case _: Throwable => flag = 1
////    }
////    assert(flag == 1)
//    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
//  }
//
//  "lexing with all semicolons" should "work" in {
//    val file = synatxErr + "statements/allSemicolons.wacc"
//    val expectedTokens: Array[String]
//    = Array("BEGIN", "SEMICOLON","SKIP", "SEMICOLON", "SKIP", "SEMICOLON",
//      "END", "SEMICOLON", "EOF")
//
//    //    var flag = 0
//    //    try {
//    //      new LexerParserTemplate(file).getLexerResult === expectedTokens
//    //      //      flag = 0
//    //    } catch {
//    //      case _: Throwable => flag = 1
//    //    }
//    //    assert(flag == 1)
//    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
//  }
//
////  failing test
//  "lexing 'Begin' with an uppercase" should "give the token ID not BEGIN" in {
//    val file = synatxErr + "basic/Begin.wacc"
//    val expectedTokens: Array[String] = Array("ID", "SKIP", "END","EOF")
//
//    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
//  }
//
////  failing test
//  "lexing multiple begins" should "give us an error (exit 100)" in {
//    val file = synatxErr + "basic/multipleBegins(noComms).wacc"
//    val expectedTokens: Array[String] = Array("BEGIN", "SKIP", "END", "BEGIN",
//      "SKIP", "END", "EOF")
//
//    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
//  }
//
//
////  failing test
//  "lexing a bad comment" should "give us an error (exit 100)" in {
//// TODO: add function to prepend syntaxErr to all files
//    val files = Array(synatxErr + "basic/badComment.wacc", synatxErr +
//      "basic/badComment2")
//    val expectedTokens: Array[Array[String]] = Array(Array(""), Array() )
//
//    (files zip expectedTokens).map{ case (file, tokens) =>
//      assert(new LexerParserTemplate(file).getLexerResult === tokens)}
//  }

//  "this" should "work" in {
//    val waccLex = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(valid + "sequence/basicSeq(noComms).wacc"))
//
//    // Get a list of matched tokens
//    val tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLex)
//
//    val tokenIDs : Array[String] =  waccLex.getRuleNames
//
//    tokens.fill()
//
//    println(tokens.getTokens)
//
//    var count = 1
//    for (i <- tokenIDs) {
//      println(i, count)
//      count += 1
//    }
//
//
//
//  }

}
