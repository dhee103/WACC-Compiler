import org.scalatest.{FlatSpec, Matchers}

class MainTest extends FlatSpec with Matchers {

  val valid: String = "wacc_examples/valid/"
  val synatxErr: String = "wacc_examples/invalid/syntaxErr/"

  //  add function to prepend strings to save space
  //  private def prepend(str: String, prep: String): Unit = {
  //    str = prep + str
  //  }

  //  valid tests

  "lexing exit statements" should "work" in {
    val files = Array(
      valid + "basic/exit/exitBasic.wacc",
      valid + "basic/exit/exitBasic2.wacc",
      valid + "basic/exit/exitWrap.wacc",
      valid + "basic/exit/exit-1.wacc"
    )

    val expectedTokens: Array[String] = Array("BEGIN", "EXIT", "INT_LITERAL",
      "END", "EOF")

    for (file <- files) {
      assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
    }

  }

  "lexing skip statements with comments" should "work" in {
    val files = Array(
      valid + "basic/skip/skip.wacc",
      valid + "basic/skip/comment.wacc",
      valid + "basic/skip/commentInLine.wacc"
    )

    val expectedTokens: Array[String] = Array("BEGIN", "SKIP_", "END", "EOF")

    for (file <- files) {
      assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
    }

  }

  "program sequence" should "work as intended" in {
    val files = Array(
      valid + "sequence/basicSeq.wacc",
      valid + "sequence/exitSimple.wacc",
      valid + "sequence/intLeadingZeros.wacc",
      valid + "sequence/intAssignment.wacc",
      valid + "sequence/boolAssignment.wacc",
      valid + "sequence/charAssignment.wacc",
      valid + "sequence/stringAssignment.wacc"
    )

    val expectedTokens: Array[Array[String]] = Array(
      Array("BEGIN", "SKIP_", "SEMICOLON", "SKIP_", "END", "EOF"),
      Array("BEGIN", "EXIT", "INT_LITERAL", "SEMICOLON", "PRINTLN", "STR_LITER",
        "END", "EOF"),
      Array("BEGIN", "INT_TYPE", "ID", "EQUALS", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUALS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "PRINTLN", "ID", "END", "EOF"),
      Array("BEGIN", "INT_TYPE", "ID", "EQUALS", "INT_LITERAL", "SEMICOLON",
        "ID", "EQUALS", "INT_LITERAL", "SEMICOLON", "EXIT", "ID", "END", "EOF"),
      Array("BEGIN", "BOOL_TYPE", "ID", "EQUALS", "FALSE_LITERAL",
        "SEMICOLON", "ID", "EQUALS", "TRUE_LITERAL", "END", "EOF"),
      Array("BEGIN", "CHAR_TYPE", "ID", "EQUALS", "CHAR_LITER", "SEMICOLON",
        "ID", "EQUALS", "CHAR_LITER", "END", "EOF"),
      Array("BEGIN", "STRING_TYPE", "ID", "EQUALS", "STR_LITER",
        "SEMICOLON", "ID", "EQUALS", "STR_LITER", "END", "EOF")
    )

    (files zip expectedTokens).map { case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)
    }

  }

  "program expressions" should "work as intended" in {
    val files = Array(
      valid + "expressions/charComparisonExpr.wacc",
      valid + "expressions/mathsExpr.wacc",
      valid + "expressions/ordAndchrExpr.wacc",
      valid + "expressions/multipleMathsOps.wacc",
      valid + "expressions/sequentialCount.wacc"
      //      valid + "expressions/charAssignment.wacc",
      //      valid + "expressions/stringAssignment.wacc"
    )

    val expectedTokens: Array[Array[String]] = Array(
      Array("BEGIN", "CHAR_TYPE", "ID", "EQUALS", "CHAR_LITER", "SEMICOLON",
        "CHAR_TYPE", "ID", "EQUALS", "CHAR_LITER", "SEMICOLON", "PRINTLN", "ID",
        "DOUBLE_EQUAL", "ID", "SEMICOLON", "PRINTLN", "ID", "NOT_EQUAL", "ID",
        "SEMICOLON", "PRINTLN", "ID", "LESS_THAN", "ID", "SEMICOLON", "PRINTLN",
        "ID", "LESS_EQUAL", "ID", "SEMICOLON", "PRINTLN", "ID", "GREATER_THAN",
        "ID", "SEMICOLON", "PRINTLN", "ID", "GREATER_EQUAL", "ID", "END",
        "EOF"),
      Array("BEGIN", "INT_TYPE", "ID", "EQUALS", "LPAREN", "INT_LITERAL",
        "MULTIPLY", "INT_LITERAL", "PLUS", "INT_LITERAL", "MULTIPLY",
        "INT_LITERAL", "RPAREN", "SEMICOLON", "IF", "ID", "DOUBLE_EQUAL",
        "INT_LITERAL", "THEN", "PRINTLN", "STR_LITER", "ELSE", "PRINTLN",
        "STR_LITER", "FI", "SEMICOLON", "BOOL_TYPE", "ID", "EQUALS",
        "EXCLAMATION", "LPAREN", "LPAREN", "TRUE_LITERAL", "LOGICAL_AND",
        "FALSE_LITERAL", "RPAREN", "LOGICAL_OR", "LPAREN", "TRUE_LITERAL",
        "LOGICAL_AND", "FALSE_LITERAL", "RPAREN", "RPAREN", "SEMICOLON", "IF",
        "ID", "DOUBLE_EQUAL", "TRUE_LITERAL", "THEN", "PRINTLN", "STR_LITER",
        "ELSE", "PRINTLN", "STR_LITER", "FI", "SEMICOLON", "INT_TYPE", "ID",
        "EQUALS", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUALS",
        "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "DIVIDE", "ID",
        "SEMICOLON", "PRINTLN", "ID", "MULTIPLY", "ID", "SEMICOLON", "PRINTLN",
        "ID", "PLUS", "ID", "SEMICOLON", "PRINTLN", "ID", "MINUS", "ID",
        "SEMICOLON", "PRINTLN", "ID", "MOD", "ID", "SEMICOLON", "INT_TYPE",
        "ID", "EQUALS", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUALS",
        "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "DIVIDE", "ID",
        "SEMICOLON", "PRINTLN", "ID", "MULTIPLY", "ID", "SEMICOLON", "PRINTLN",
        "ID", "PLUS", "ID", "SEMICOLON", "PRINTLN", "ID", "MINUS", "ID",
        "SEMICOLON", "PRINTLN", "ID", "MOD", "ID", "END", "EOF"),
      Array("BEGIN", "CHAR_TYPE", "ID", "EQUALS", "CHAR_LITER", "SEMICOLON",
        "INT_TYPE", "ID", "EQUALS", "INT_LITERAL", "SEMICOLON", "PRINT", "ID",
        "SEMICOLON", "PRINT", "STR_LITER", "SEMICOLON", "PRINTLN", "ORD", "ID",
        "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT", "STR_LITER",
        "SEMICOLON", "PRINTLN", "CHR", "ID", "END", "EOF"),
      Array("BEGIN", "PRINTLN", "INT_LITERAL", "PLUS", "INT_LITERAL",
        "SEMICOLON", "PRINTLN", "INT_LITERAL", "PLUS", "INT_LITERAL",
        "SEMICOLON", "PRINTLN", "INT_LITERAL", "MINUS", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUALS", "INT_LITERAL", "SEMICOLON",
        "PRINTLN", "MINUS", "ID", "END", "EOF"),
      Array("BEGIN", "INT_TYPE", "ID", "EQUALS", "INT_LITERAL", "SEMICOLON",
        "PRINTLN", "STR_LITER", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON",
        "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN",
        "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITEL",
        "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS",
        "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID",
        "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON",
        "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS",
        "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID",
        "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON",
        "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS",
        "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "END", "EOF")

//    "PRINTLN", "STR_LITER", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID", "EQUALS", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "END", "EOF"


    )

    (files zip expectedTokens).map { case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)
    }


  }




  //  ////  passing test
  //  "lexing with no semicolons" should "work" in {
  //    val file = synatxErr + "statements/noSemicolons.wacc"
  //    val expectedTokens: Array[String]
  //    = Array("BEGIN", "SKIP_", "SKIP_", "END", "EOF")
  //
  //    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  //  }
  //
  //  "lexing with all semicolons" should "work" in {
  //    val file = synatxErr + "statements/allSemicolons.wacc"
  //    val expectedTokens: Array[String]
  //    = Array("BEGIN", "SEMICOLON","SKIP_", "SEMICOLON", "SKIP_", "SEMICOLON",
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
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  ////  The below tests are all failing tests and are more specialised
  ////  failing test
  //  "lexing 'beginning'" should "give us the token ID not BEGIN" in {
  //    val file = synatxErr + "basic/beginning.wacc"
  //    val expectedTokens: Array[String] = Array("ID", "SKIP_", "SEMICOLON",
  //      "SKIP_", "END","EOF")
  //    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  //  }
  //
  //  //failing test
  //  //TODO: find out why this isn't throwing an error
  //  "lexing '1begin'" should "throw error" in {
  //    val file = synatxErr + "statements/startWithNumberTest.wacc"
  //    val expectedTokens: Array[String] = Array("BEGIN", "SKIP_", "END","EOF")
  //    println(new LexerParserTemplate(file).getLexerResult.mkString(" "))
  //    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  //    try {
  //      new LexerParserTemplate(file).getLexerResult === expectedTokens
  //      fail("This test should fail")
  //    } catch {
  //      case _: Throwable => succeed
  //    }
  //  }
  //
  //
  ////
  //////  failing test
  //  "lexing 'Begin' with an uppercase" should "give the token ID not BEGIN"
  // in {
  //    val file = synatxErr + "basic/Begin.wacc"
  //    val expectedTokens: Array[String] = Array("ID", "SKIP_", "END","EOF")
  //
  //    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  //  }
  ////
  //////  failing test
  //  "lexing multiple begins" should "give us an error (exit 100)" in {
  //    val file = synatxErr + "basic/multipleBegins(noComms).wacc"
  //    val expectedTokens: Array[String] = Array("BEGIN", "SKIP_", "END",
  // "BEGIN",
  //      "SKIP_", "END", "EOF")
  //
  //    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  //  }
  //
  //
  //////  failing test
  ////  "lexing a bad comment" should "give us an error (exit 100)" in {
  ////// TODO: add function to prepend syntaxErr to all files
  ////    val files = Array(synatxErr + "basic/badComment.wacc", synatxErr +
  ////      "basic/badComment2")
  ////    val expectedTokens: Array[Array[String]] = Array(Array(""), Array() )
  ////
  ////    (files zip expectedTokens).map{ case (file, tokens) =>
  ////      assert(new LexerParserTemplate(file).getLexerResult === tokens)}
  ////  }
  //
  ////  "this" should "work" in {
  ////    val waccLex = new WaccLexer(new org.antlr.v4.runtime
  // .ANTLRFileStream(valid + "sequence/basicSeq(noComms).wacc"))
  ////
  ////    // Get a list of matched tokens
  ////    val tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLex)
  ////
  ////    val tokenIDs : Array[String] =  waccLex.getRuleNames
  ////
  ////    tokens.fill()
  ////
  ////    println(tokens.getTokens)
  ////
  ////    var count = 1
  ////    for (i <- tokenIDs) {
  ////      println(i, count)
  ////      count += 1
  ////    }
  ////  }

}
