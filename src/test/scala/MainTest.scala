import org.scalatest.{FlatSpec, Matchers}

class MainTest extends FlatSpec with Matchers {

  val valid: String = "wacc_examples/valid/"
  val synatxErr: String = "wacc_examples/invalid/syntaxErr/"

  //  TODO: Add better test names


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

      Array("BEGIN", "EXIT", "INT_LITERAL", "SEMICOLON", "PRINTLN",
        "STR_LITERAL",
        "END", "EOF"),

      Array("BEGIN", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "PRINTLN", "ID", "END", "EOF"),

      Array("BEGIN", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "EXIT", "ID", "END", "EOF"),

      Array("BEGIN", "BOOL_TYPE", "ID", "EQUAL", "FALSE_LITERAL",
        "SEMICOLON", "ID", "EQUAL", "TRUE_LITERAL", "END", "EOF"),

      Array("BEGIN", "CHAR_TYPE", "ID", "EQUAL", "CHAR_LITERAL", "SEMICOLON",
        "ID", "EQUAL", "CHAR_LITERAL", "END", "EOF"),

      Array("BEGIN", "STRING_TYPE", "ID", "EQUAL", "STR_LITERAL",
        "SEMICOLON", "ID", "EQUAL", "STR_LITERAL", "END", "EOF")
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
    )

    val expectedTokens: Array[Array[String]] = Array(
      Array("BEGIN", "CHAR_TYPE", "ID", "EQUAL", "CHAR_LITERAL", "SEMICOLON",
        "CHAR_TYPE", "ID", "EQUAL", "CHAR_LITERAL", "SEMICOLON", "PRINTLN",
        "ID",
        "DOUBLE_EQUAL", "ID", "SEMICOLON", "PRINTLN", "ID", "NOT_EQUAL", "ID",
        "SEMICOLON", "PRINTLN", "ID", "LESS_THAN", "ID", "SEMICOLON", "PRINTLN",
        "ID", "LESS_EQUAL", "ID", "SEMICOLON", "PRINTLN", "ID", "GREATER_THAN",
        "ID", "SEMICOLON", "PRINTLN", "ID", "GREATER_EQUAL", "ID", "END",
        "EOF"),

      Array("BEGIN", "INT_TYPE", "ID", "EQUAL", "LPAREN", "INT_LITERAL",
        "MULTIPLY", "INT_LITERAL", "PLUS", "INT_LITERAL", "MULTIPLY",
        "INT_LITERAL", "RPAREN", "SEMICOLON", "IF", "ID", "DOUBLE_EQUAL",
        "INT_LITERAL", "THEN", "PRINTLN", "STR_LITERAL", "ELSE", "PRINTLN",
        "STR_LITERAL", "FI", "SEMICOLON", "BOOL_TYPE", "ID", "EQUAL",
        "EXCLAMATION", "LPAREN", "LPAREN", "TRUE_LITERAL", "LOGICAL_AND",
        "FALSE_LITERAL", "RPAREN", "LOGICAL_OR", "LPAREN", "TRUE_LITERAL",
        "LOGICAL_AND", "FALSE_LITERAL", "RPAREN", "RPAREN", "SEMICOLON", "IF",
        "ID", "DOUBLE_EQUAL", "TRUE_LITERAL", "THEN", "PRINTLN", "STR_LITERAL",
        "ELSE", "PRINTLN", "STR_LITERAL", "FI", "SEMICOLON", "INT_TYPE", "ID",
        "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "DIVIDE", "ID",
        "SEMICOLON", "PRINTLN", "ID", "MULTIPLY", "ID", "SEMICOLON", "PRINTLN",
        "ID", "PLUS", "ID", "SEMICOLON", "PRINTLN", "ID", "MINUS", "ID",
        "SEMICOLON", "PRINTLN", "ID", "MOD", "ID", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "DIVIDE", "ID",
        "SEMICOLON", "PRINTLN", "ID", "MULTIPLY", "ID", "SEMICOLON", "PRINTLN",
        "ID", "PLUS", "ID", "SEMICOLON", "PRINTLN", "ID", "MINUS", "ID",
        "SEMICOLON", "PRINTLN", "ID", "MOD", "ID", "END", "EOF"),

      Array("BEGIN", "CHAR_TYPE", "ID", "EQUAL", "CHAR_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "PRINT", "ID",
        "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON", "PRINTLN", "ORD",
        "ID",
        "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT", "STR_LITERAL",
        "SEMICOLON", "PRINTLN", "CHR", "ID", "END", "EOF"),

      Array("BEGIN", "PRINTLN", "INT_LITERAL", "PLUS", "INT_LITERAL",
        "SEMICOLON", "PRINTLN", "INT_LITERAL", "PLUS", "INT_LITERAL",
        "SEMICOLON", "PRINTLN", "INT_LITERAL", "MINUS", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "PRINTLN", "MINUS", "ID", "END", "EOF"),

      Array("BEGIN", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "PRINTLN", "STR_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON",
        "ID", "EQUAL", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN",
        "ID", "SEMICOLON", "ID", "EQUAL", "ID", "PLUS", "INT_LITERAL",
        "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID", "EQUAL", "ID", "PLUS",
        "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID",
        "EQUAL", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "ID", "EQUAL", "ID", "PLUS", "INT_LITERAL", "SEMICOLON",
        "PRINTLN", "ID", "SEMICOLON", "ID", "EQUAL", "ID", "PLUS",
        "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "ID",
        "EQUAL", "ID", "PLUS", "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "ID", "EQUAL", "ID", "PLUS", "INT_LITERAL", "SEMICOLON",
        "PRINTLN", "ID", "SEMICOLON", "ID", "EQUAL", "ID", "PLUS",
        "INT_LITERAL", "SEMICOLON", "PRINTLN", "ID", "END", "EOF")
    )

    (files zip expectedTokens).map { case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)
    }

  }

  "if programs" should "work as intended" in {
    val file = valid + "if/ifTest.wacc"

    val expectedTokens: Array[String] = Array(
      "BEGIN", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
      "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "BOOL_TYPE", "ID",
      "EQUAL", "TRUE_LITERAL", "SEMICOLON", "BOOL_TYPE", "ID", "EQUAL",
      "FALSE_LITERAL", "SEMICOLON", "IF", "ID", "LOGICAL_AND", "ID",
      "LOGICAL_OR", "ID", "GREATER_EQUAL", "ID", "LOGICAL_OR", "ID",
      "DOUBLE_EQUAL", "INT_LITERAL", "THEN", "PRINTLN", "ID", "ELSE", "PRINTLN",
      "ID", "FI", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)

  }

  "scope" should "work as intended" in {
    val files = Array(
      valid + "scope/scopeTest.wacc",
      valid + "scope/scopeRedefine.wacc",
      valid + "scope/printAllTypes.wacc"
    )

    val expectedTokens: Array[Array[String]] = Array(
      Array("BEGIN", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "IF", "ID", "DOUBLE_EQUAL", "INT_LITERAL", "THEN", "IF", "ID",
        "GREATER_THAN", "INT_LITERAL", "THEN", "IF", "ID", "LESS_THAN",
        "INT_LITERAL", "THEN", "PRINTLN", "STR_LITERAL", "ELSE", "IF", "ID",
        "GREATER_THAN", "INT_LITERAL", "THEN", "IF", "ID", "GREATER_THAN",
        "INT_LITERAL", "THEN", "PRINTLN", "STR_LITERAL", "ELSE", "PRINTLN",
        "STR_LITERAL", "FI", "ELSE", "PRINTLN", "STR_LITERAL", "FI", "FI",
        "ELSE", "PRINTLN", "STR_LITERAL", "FI", "ELSE", "PRINTLN",
        "STR_LITERAL",
        "FI", "END", "EOF"),

      Array("BEGIN", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "BEGIN", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "BOOL_TYPE",
        "ID", "EQUAL", "TRUE_LITERAL", "SEMICOLON", "PRINTLN", "ID", "END",
        "SEMICOLON", "PRINTLN", "ID", "END", "EOF"),


      Array("BEGIN", "STRING_TYPE", "ID", "EQUAL", "STR_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "BEGIN",
        "CHAR_TYPE", "ID", "EQUAL", "CHAR_LITERAL", "SEMICOLON", "BEGIN",
        "BOOL_TYPE", "ID", "EQUAL", "TRUE_LITERAL", "SEMICOLON", "BEGIN",
        "STRING_TYPE", "ID", "EQUAL", "STR_LITERAL", "SEMICOLON", "BEGIN",
        "INT_TYPE", "LBRACKET", "RBRACKET", "ID", "EQUAL", "LBRACKET",
        "INT_LITERAL", "COMMA", "INT_LITERAL", "COMMA", "INT_LITERAL",
        "RBRACKET", "SEMICOLON", "BEGIN", "CHAR_TYPE", "LBRACKET",
        "RBRACKET", "ID", "EQUAL", "LBRACKET", "CHAR_LITERAL", "COMMA",
        "CHAR_LITERAL", "COMMA", "CHAR_LITERAL", "RBRACKET", "SEMICOLON",
        "BEGIN", "BOOL_TYPE", "LBRACKET", "RBRACKET", "ID", "EQUAL",
        "LBRACKET", "TRUE_LITERAL", "COMMA", "FALSE_LITERAL", "COMMA",
        "TRUE_LITERAL", "RBRACKET", "SEMICOLON", "BEGIN", "STRING_TYPE",
        "LBRACKET", "RBRACKET", "ID", "EQUAL", "LBRACKET", "STR_LITERAL",
        "COMMA", "STR_LITERAL", "COMMA", "STR_LITERAL", "RBRACKET", "SEMICOLON",
        "BEGIN", "PAIR_TYPE", "LPAREN", "INT_TYPE", "COMMA", "INT_TYPE",
        "RPAREN", "ID", "EQUAL", "NEWPAIR", "LPAREN", "INT_LITERAL",
        "COMMA", "INT_LITERAL", "RPAREN", "SEMICOLON", "BEGIN", "PAIR_TYPE",
        "LPAREN", "CHAR_TYPE", "COMMA", "BOOL_TYPE", "RPAREN", "ID",
        "EQUAL", "NEWPAIR", "LPAREN", "CHAR_LITERAL", "COMMA", "TRUE_LITERAL",
        "RPAREN", "SEMICOLON", "PAIR_TYPE", "LPAREN", "CHAR_TYPE", "COMMA",
        "BOOL_TYPE", "RPAREN", "ID", "EQUAL", "NEWPAIR", "LPAREN",
        "CHAR_LITERAL", "COMMA", "FALSE_LITERAL", "RPAREN", "SEMICOLON",
        "PAIR_TYPE", "LPAREN", "CHAR_TYPE", "COMMA", "BOOL_TYPE", "RPAREN",
        "LBRACKET", "RBRACKET", "ID", "EQUAL", "LBRACKET", "ID", "COMMA",
        "ID", "RBRACKET", "SEMICOLON", "BEGIN", "INT_TYPE", "LBRACKET",
        "RBRACKET", "ID", "EQUAL", "LBRACKET", "INT_LITERAL", "COMMA",
        "INT_LITERAL", "COMMA", "INT_LITERAL", "RBRACKET", "SEMICOLON",
        "CHAR_TYPE", "LBRACKET", "RBRACKET", "ID", "EQUAL", "LBRACKET",
        "CHAR_LITERAL", "COMMA", "CHAR_LITERAL", "COMMA", "CHAR_LITERAL",
        "RBRACKET", "SEMICOLON", "PAIR_TYPE", "LPAREN", "INT_TYPE",
        "LBRACKET", "RBRACKET", "COMMA", "CHAR_TYPE", "LBRACKET", "RBRACKET",
        "RPAREN", "ID", "EQUAL", "NEWPAIR", "LPAREN", "ID", "COMMA", "ID",
        "RPAREN", "SEMICOLON", "BEGIN", "SKIP_", "END", "SEMICOLON",
        "INT_TYPE", "LBRACKET", "RBRACKET", "ID", "EQUAL", "FIRST", "ID",
        "SEMICOLON", "CHAR_TYPE", "LBRACKET", "RBRACKET", "ID", "EQUAL",
        "SECOND", "ID", "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON",
        "PRINT", "ID", "LBRACKET", "INT_LITERAL", "RBRACKET", "SEMICOLON",
        "PRINT", "ID", "SEMICOLON", "PRINT", "ID", "LBRACKET", "INT_LITERAL",
        "RBRACKET", "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT", "ID",
        "LBRACKET", "INT_LITERAL", "RBRACKET", "SEMICOLON", "PRINT",
        "STR_LITERAL", "SEMICOLON", "PRINT", "ID", "LBRACKET", "INT_LITERAL",
        "RBRACKET", "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT", "ID",
        "LBRACKET", "INT_LITERAL", "RBRACKET", "SEMICOLON", "PRINT", "ID",
        "SEMICOLON", "PRINT", "ID", "LBRACKET", "INT_LITERAL", "RBRACKET",
        "SEMICOLON", "PRINTLN", "STR_LITERAL", "END", "SEMICOLON", "PAIR_TYPE",
        "LPAREN", "CHAR_TYPE", "COMMA", "BOOL_TYPE", "RPAREN", "ID",
        "EQUAL", "ID", "LBRACKET", "INT_LITERAL", "RBRACKET", "SEMICOLON",
        "CHAR_TYPE", "ID", "EQUAL", "FIRST", "ID", "SEMICOLON", "BOOL_TYPE",
        "ID", "EQUAL", "SECOND", "ID", "SEMICOLON", "PAIR_TYPE", "LPAREN",
        "CHAR_TYPE", "COMMA", "BOOL_TYPE", "RPAREN", "ID", "EQUAL", "ID",
        "LBRACKET", "INT_LITERAL", "RBRACKET", "SEMICOLON", "CHAR_TYPE",
        "ID", "EQUAL", "FIRST", "ID", "SEMICOLON", "BOOL_TYPE", "ID",
        "EQUAL", "SECOND", "ID", "SEMICOLON", "PRINT", "STR_LITERAL",
        "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT", "STR_LITERAL",
        "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT", "ID", "SEMICOLON",
        "PRINT", "ID", "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON",
        "PRINT", "ID", "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON",
        "PRINT", "ID", "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT",
        "ID", "SEMICOLON", "PRINTLN", "STR_LITERAL", "END", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "FIRST", "ID", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "SECOND", "ID", "SEMICOLON", "PRINT", "ID",
        "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINTLN", "ID", "END",
        "SEMICOLON", "STRING_TYPE", "ID", "EQUAL", "ID", "LBRACKET",
        "INT_LITERAL", "RBRACKET", "SEMICOLON", "STRING_TYPE", "ID",
        "EQUAL", "ID", "LBRACKET", "INT_LITERAL", "RBRACKET", "SEMICOLON",
        "STRING_TYPE", "ID", "EQUAL", "ID", "LBRACKET", "INT_LITERAL",
        "RBRACKET", "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT", "ID",
        "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT", "ID", "SEMICOLON",
        "PRINTLN", "ID", "END", "SEMICOLON", "PRINT", "ID", "LBRACKET",
        "INT_LITERAL", "RBRACKET", "SEMICOLON", "PRINT", "ID", "SEMICOLON",
        "PRINT", "ID", "LBRACKET", "INT_LITERAL", "RBRACKET", "SEMICOLON",
        "PRINT", "ID", "SEMICOLON", "PRINTLN", "ID", "LBRACKET",
        "INT_LITERAL", "RBRACKET", "END", "SEMICOLON", "PRINTLN", "ID",
        "END", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "ID", "LBRACKET",
        "INT_LITERAL", "RBRACKET", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "ID", "LBRACKET", "INT_LITERAL", "RBRACKET", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "ID", "LBRACKET", "INT_LITERAL", "RBRACKET",
        "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT", "ID", "SEMICOLON",
        "PRINT", "ID", "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINTLN",
        "ID", "END", "SEMICOLON", "PRINTLN", "ID", "END", "SEMICOLON",
        "PRINTLN", "ID", "END", "SEMICOLON", "PRINTLN", "ID", "END",
        "SEMICOLON", "PRINTLN", "ID", "END", "EOF")
    )

    (files zip expectedTokens).map { case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)
    }

  }

  "arrays" should "work as intended" in {
    val files = Array(
      valid + "array/array.wacc",
      valid + "array/arrayNested.wacc",
      valid + "array/modifyString.wacc"
    )

    val expectedTokens: Array[Array[String]] = Array(
      Array("BEGIN", "INT_TYPE", "LBRACKET", "RBRACKET", "ID", "EQUAL",
        "LBRACKET",
        "INT_LITERAL", "COMMA", "INT_LITERAL", "COMMA", "INT_LITERAL", "COMMA",
        "INT_LITERAL", "COMMA", "INT_LITERAL", "COMMA", "INT_LITERAL", "COMMA",
        "INT_LITERAL", "COMMA", "INT_LITERAL", "COMMA", "INT_LITERAL", "COMMA",
        "INT_LITERAL", "RBRACKET", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "WHILE", "ID", "LESS_THAN", "LENGTH", "ID",
        "DO", "ID", "LBRACKET", "ID", "RBRACKET", "EQUAL", "ID", "SEMICOLON",
        "ID", "EQUAL", "ID", "PLUS", "INT_LITERAL", "DONE", "SEMICOLON",
        "PRINT", "ID", "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON", "ID",
        "EQUAL", "INT_LITERAL", "SEMICOLON", "WHILE", "ID", "LESS_THAN",
        "INT_LITERAL", "DO", "PRINT", "ID", "LBRACKET", "ID", "RBRACKET",
        "SEMICOLON", "IF", "ID", "LESS_THAN", "INT_LITERAL", "THEN", "PRINT",
        "STR_LITERAL", "ELSE", "SKIP_", "FI", "SEMICOLON", "ID", "EQUAL", "ID",
        "PLUS", "INT_LITERAL", "DONE", "SEMICOLON", "PRINTLN", "STR_LITERAL",
        "SEMICOLON", "PRINTLN", "ID", "LBRACKET", "INT_LITERAL", "RBRACKET",
        "SEMICOLON", "INT_TYPE", "LBRACKET", "RBRACKET", "ID", "EQUAL",
        "LBRACKET", "RBRACKET", "SEMICOLON", "PRINTLN", "ID", "END", "EOF"),

      Array("BEGIN", "INT_TYPE", "LBRACKET", "RBRACKET", "ID", "EQUAL",
        "LBRACKET", "INT_LITERAL", "COMMA", "INT_LITERAL", "COMMA",
        "INT_LITERAL", "RBRACKET", "SEMICOLON", "INT_TYPE", "LBRACKET",
        "RBRACKET", "ID", "EQUAL", "LBRACKET", "INT_LITERAL", "COMMA",
        "INT_LITERAL", "RBRACKET", "SEMICOLON", "INT_TYPE", "LBRACKET",
        "RBRACKET", "LBRACKET", "RBRACKET", "ID", "EQUAL", "LBRACKET", "ID",
        "COMMA", "ID", "RBRACKET", "SEMICOLON", "PRINTLN", "ID", "LBRACKET",
        "INT_LITERAL", "RBRACKET", "LBRACKET", "INT_LITERAL", "RBRACKET",
        "SEMICOLON", "PRINTLN", "ID", "LBRACKET", "INT_LITERAL", "RBRACKET",
        "LBRACKET", "INT_LITERAL", "RBRACKET", "END", "EOF"),

      Array("BEGIN", "STRING_TYPE", "ID", "EQUAL", "STR_LITERAL", "SEMICOLON",
        "PRINTLN", "ID", "SEMICOLON", "ID", "LBRACKET", "INT_LITERAL",
        "RBRACKET", "EQUAL", "CHAR_LITERAL", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "ID", "EQUAL", "STR_LITERAL", "SEMICOLON", "PRINTLN", "ID",
        "END", "EOF")
    )

    (files zip expectedTokens).map { case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)
    }

  }

  "assigning and reading variables" should "work as intended" in {
    val file = valid + "variables/manyVariables.wacc"

    val expectedTokens: Array[String] =
      Array("BEGIN", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)

  }

  "pairs" should "work as intended" in {
    val files = Array(
      valid + "pairs/checkRefPair.wacc",
      valid + "pairs/linkedList.wacc"
    )

    val expectedTokens: Array[Array[String]] = Array(
      Array("BEGIN", "PAIR_TYPE", "LPAREN", "INT_TYPE", "COMMA", "CHAR_TYPE",
        "RPAREN", "ID", "EQUAL", "NEWPAIR", "LPAREN", "INT_LITERAL", "COMMA",
        "CHAR_LITERAL", "RPAREN", "SEMICOLON", "PAIR_TYPE", "LPAREN",
        "INT_TYPE", "COMMA", "CHAR_TYPE", "RPAREN", "ID", "EQUAL", "ID",
        "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "PRINTLN", "ID", "DOUBLE_EQUAL", "ID", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "FIRST", "ID", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "FIRST", "ID", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "PRINTLN", "ID",
        "DOUBLE_EQUAL", "ID", "SEMICOLON", "CHAR_TYPE", "ID", "EQUAL",
        "SECOND", "ID", "SEMICOLON", "CHAR_TYPE", "ID", "EQUAL", "SECOND",
        "ID", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "PRINTLN", "ID", "DOUBLE_EQUAL", "ID", "SEMICOLON",
        "FIRST", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "SECOND", "ID",
        "EQUAL", "CHAR_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "FIRST", "ID", "SEMICOLON", "CHAR_TYPE", "ID", "EQUAL", "SECOND",
        "ID", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "ID", "EQUAL", "NULL", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "PRINTLN", "NULL", "SEMICOLON", "FREE", "ID",
        "SEMICOLON", "FREE", "ID", "END", "EOF"),

      Array("BEGIN", "PAIR_TYPE", "LPAREN", "INT_TYPE", "COMMA", "PAIR_TYPE",
        "RPAREN", "ID", "EQUAL", "NEWPAIR", "LPAREN", "INT_LITERAL", "COMMA",
        "NULL", "RPAREN", "SEMICOLON", "PAIR_TYPE", "LPAREN", "INT_TYPE",
        "COMMA", "PAIR_TYPE", "RPAREN", "ID", "EQUAL", "NEWPAIR", "LPAREN",
        "INT_LITERAL", "COMMA", "ID", "RPAREN", "SEMICOLON", "PAIR_TYPE",
        "LPAREN", "INT_TYPE", "COMMA", "PAIR_TYPE", "RPAREN", "ID", "EQUAL",
        "NEWPAIR", "LPAREN", "INT_LITERAL", "COMMA", "ID", "RPAREN",
        "SEMICOLON", "PAIR_TYPE", "LPAREN", "INT_TYPE", "COMMA", "PAIR_TYPE",
        "RPAREN", "ID", "EQUAL", "NEWPAIR", "LPAREN", "INT_LITERAL", "COMMA",
        "ID", "RPAREN", "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON",
        "PAIR_TYPE", "LPAREN", "INT_TYPE", "COMMA", "PAIR_TYPE", "RPAREN",
        "ID", "EQUAL", "ID", "SEMICOLON", "PAIR_TYPE", "LPAREN", "INT_TYPE",
        "COMMA", "PAIR_TYPE", "RPAREN", "ID", "EQUAL", "SECOND", "ID",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "WHILE", "ID", "NOT_EQUAL", "NULL", "DO", "ID", "EQUAL", "FIRST",
        "ID", "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT",
        "STR_LITERAL", "SEMICOLON", "ID", "EQUAL", "ID", "SEMICOLON", "ID",
        "EQUAL", "SECOND", "ID", "DONE", "SEMICOLON", "ID", "EQUAL", "FIRST",
        "ID", "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINTLN",
        "STR_LITERAL", "END", "EOF")
    )

    (files zip expectedTokens).map { case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)
    }

  }

  "IO" should "work as intended" in {
    val files = Array(
      valid + "IO/IOLoop.wacc",
      valid + "IO/print/multiplePrints.wacc",
      valid + "IO/read/echoBigNegInt.wacc"
    )

    val expectedTokens: Array[Array[String]] = Array(
      Array("BEGIN", "CHAR_TYPE", "ID", "EQUAL", "CHAR_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "WHILE", "ID",
        "NOT_EQUAL", "CHAR_LITERAL", "DO", "PRINT", "STR_LITERAL", "SEMICOLON",
        "READ", "ID", "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON",
        "PRINTLN", "ID", "SEMICOLON", "PRINTLN", "STR_LITERAL", "SEMICOLON",
        "PRINTLN", "STR_LITERAL", "SEMICOLON", "READ", "ID", "DONE", "END",
        "EOF"),

      Array("BEGIN", "PRINTLN", "STR_LITERAL", "SEMICOLON", "PRINT",
        "STR_LITERAL", "SEMICOLON", "PRINTLN", "TRUE_LITERAL", "SEMICOLON",
        "PRINT", "STR_LITERAL", "SEMICOLON", "PRINTLN", "CHAR_LITERAL",
        "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON", "PRINTLN",
        "CHAR_LITERAL", "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON",
        "PRINTLN", "INT_LITERAL", "SEMICOLON", "STRING_TYPE", "ID", "EQUAL",
        "STR_LITERAL", "SEMICOLON", "STRING_TYPE", "ID", "EQUAL",
        "STR_LITERAL", "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON",
        "PRINTLN", "ID", "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON",
        "PRINTLN", "ID", "SEMICOLON", "IF", "ID", "DOUBLE_EQUAL", "ID",
        "THEN", "PRINTLN", "STR_LITERAL", "ELSE", "PRINTLN", "STR_LITERAL",
        "FI", "SEMICOLON", "PRINTLN", "STR_LITERAL", "SEMICOLON", "ID",
        "LBRACKET", "INT_LITERAL", "RBRACKET", "EQUAL", "CHAR_LITERAL",
        "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON", "PRINTLN", "ID",
        "SEMICOLON", "IF", "ID", "DOUBLE_EQUAL", "ID", "THEN", "PRINTLN",
        "STR_LITERAL", "ELSE", "PRINTLN", "STR_LITERAL", "FI", "END", "EOF"),

      Array("BEGIN", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "PRINTLN", "STR_LITERAL", "SEMICOLON", "READ", "ID", "SEMICOLON",
        "PRINTLN", "ID", "END", "EOF")
    )

    (files zip expectedTokens).map { case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)
    }

  }

  "whiles" should "work as intended" in {
    val files = Array(
      valid + "while/fibonacciFullIt.wacc",
      valid + "while/rmStyleAddIO.wacc"
    )

    val expectedTokens: Array[Array[String]] = Array(
      Array("BEGIN", "PRINTLN", "STR_LITERAL", "SEMICOLON", "PRINT",
        "STR_LITERAL", "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL",
        "SEMICOLON", "READ", "ID", "SEMICOLON", "PRINT", "STR_LITERAL",
        "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "PRINT", "STR_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE",
        "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "WHILE", "ID",
        "GREATER_THAN", "INT_LITERAL", "DO", "ID", "EQUAL", "ID",
        "SEMICOLON", "ID", "EQUAL", "ID", "SEMICOLON", "ID", "EQUAL", "ID",
        "PLUS", "ID", "SEMICOLON", "ID", "EQUAL", "ID", "MINUS",
        "INT_LITERAL", "DONE", "SEMICOLON", "PRINTLN", "ID", "END", "EOF"),

      Array("BEGIN", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "PRINT",
        "STR_LITERAL", "SEMICOLON", "READ", "ID", "SEMICOLON", "PRINT",
        "STR_LITERAL", "SEMICOLON", "READ", "ID", "SEMICOLON", "PRINT",
        "STR_LITERAL", "SEMICOLON", "PRINTLN", "ID", "SEMICOLON", "WHILE",
        "ID", "GREATER_THAN", "INT_LITERAL", "DO", "PRINT", "STR_LITERAL",
        "SEMICOLON", "ID", "EQUAL", "ID", "PLUS", "INT_LITERAL", "SEMICOLON",
        "ID", "EQUAL", "ID", "MINUS", "INT_LITERAL", "DONE", "SEMICOLON",
        "PRINTLN", "STR_LITERAL", "SEMICOLON", "PRINT", "STR_LITERAL",
        "SEMICOLON", "PRINTLN", "ID", "END", "EOF")
    )

    (files zip expectedTokens).map { case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)
    }

  }

  "functions" should "work as intended" in {
    val files = Array(
      valid + "function/simple_functions/asciiTable.wacc",
      valid + "function/nested_functions/fibonacciRecursive.wacc"
    )

    val expectedTokens: Array[Array[String]] = Array(
      Array("BEGIN", "BOOL_TYPE", "ID", "LPAREN", "INT_TYPE", "ID", "RPAREN",
        "IS", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "WHILE",
        "ID", "LESS_THAN", "ID", "DO", "PRINT", "STR_LITERAL", "SEMICOLON",
        "ID", "EQUAL", "ID", "PLUS", "INT_LITERAL", "DONE", "SEMICOLON",
        "PRINTLN", "STR_LITERAL", "SEMICOLON", "RETURN", "TRUE_LITERAL",
        "END", "BOOL_TYPE", "ID", "LPAREN", "INT_TYPE", "ID", "RPAREN", "IS",
        "PRINT", "STR_LITERAL", "SEMICOLON", "IF", "ID", "LESS_THAN",
        "INT_LITERAL", "THEN", "PRINT", "STR_LITERAL", "ELSE", "SKIP_", "FI",
        "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT", "STR_LITERAL",
        "SEMICOLON", "PRINT", "CHR", "ID", "SEMICOLON", "PRINTLN",
        "STR_LITERAL", "SEMICOLON", "RETURN", "TRUE_LITERAL", "END",
        "PRINTLN", "STR_LITERAL", "SEMICOLON", "BOOL_TYPE", "ID", "EQUAL",
        "CALL", "ID", "LPAREN", "INT_LITERAL", "RPAREN", "SEMICOLON",
        "INT_TYPE", "ID", "EQUAL", "ORD", "CHAR_LITERAL", "SEMICOLON",
        "WHILE", "ID", "LESS_THAN", "INT_LITERAL", "DO", "ID", "EQUAL",
        "CALL", "ID", "LPAREN", "ID", "RPAREN", "SEMICOLON", "ID", "EQUAL",
        "ID", "PLUS", "INT_LITERAL", "DONE", "SEMICOLON", "ID", "EQUAL",
        "CALL", "ID", "LPAREN", "INT_LITERAL", "RPAREN", "END", "EOF"),

      Array("BEGIN", "INT_TYPE", "ID", "LPAREN", "INT_TYPE", "ID", "COMMA",
        "BOOL_TYPE", "ID", "RPAREN", "IS", "IF", "ID", "LESS_EQUAL",
        "INT_LITERAL", "THEN", "RETURN", "ID", "ELSE", "SKIP_", "FI",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "CALL", "ID", "LPAREN", "ID",
        "MINUS", "INT_LITERAL", "COMMA", "ID", "RPAREN", "SEMICOLON", "IF",
        "ID", "THEN", "PRINT", "ID", "SEMICOLON", "PRINT", "STR_LITERAL",
        "ELSE", "SKIP_", "FI", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
        "CALL", "ID", "LPAREN", "ID", "MINUS", "INT_LITERAL", "COMMA",
        "FALSE_LITERAL", "RPAREN", "SEMICOLON", "RETURN", "ID", "PLUS", "ID",
        "END", "PRINTLN", "STR_LITERAL", "SEMICOLON", "PRINT", "STR_LITERAL",
        "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "CALL", "ID", "LPAREN",
        "INT_LITERAL", "COMMA", "TRUE_LITERAL", "RPAREN", "SEMICOLON",
        "PRINT", "ID", "SEMICOLON", "PRINTLN", "STR_LITERAL", "END", "EOF")
    )

    (files zip expectedTokens).map { case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)
    }

  }

  "advanced functions" should "work as intended" in {
    val file = valid + "advanced/binarySortTree.wacc"

    val expectedTokens = Array("BEGIN", "PAIR_TYPE", "LPAREN", "INT_TYPE",
      "COMMA", "PAIR_TYPE", "RPAREN", "ID", "LPAREN", "INT_TYPE", "ID",
      "COMMA", "PAIR_TYPE", "LPAREN", "INT_TYPE", "COMMA", "PAIR_TYPE",
      "RPAREN", "ID", "COMMA", "PAIR_TYPE", "LPAREN", "INT_TYPE", "COMMA",
      "PAIR_TYPE", "RPAREN", "ID", "RPAREN", "IS", "PAIR_TYPE", "LPAREN",
      "PAIR_TYPE", "COMMA", "PAIR_TYPE", "RPAREN", "ID", "EQUAL", "NEWPAIR",
      "LPAREN", "ID", "COMMA", "ID", "RPAREN", "SEMICOLON", "PAIR_TYPE",
      "LPAREN", "INT_TYPE", "COMMA", "PAIR_TYPE", "RPAREN", "ID", "EQUAL",
      "NEWPAIR", "LPAREN", "ID", "COMMA", "ID", "RPAREN", "SEMICOLON",
      "RETURN", "ID", "END", "PAIR_TYPE", "LPAREN", "INT_TYPE", "COMMA",
      "PAIR_TYPE", "RPAREN", "ID", "LPAREN", "PAIR_TYPE", "LPAREN",
      "INT_TYPE", "COMMA", "PAIR_TYPE", "RPAREN", "ID", "COMMA", "INT_TYPE",
      "ID", "RPAREN", "IS", "IF", "ID", "DOUBLE_EQUAL", "NULL", "THEN", "ID",
      "EQUAL", "CALL", "ID", "LPAREN", "ID", "COMMA", "NULL", "COMMA",
      "NULL", "RPAREN", "ELSE", "PAIR_TYPE", "LPAREN", "PAIR_TYPE", "COMMA",
      "PAIR_TYPE", "RPAREN", "ID", "EQUAL", "SECOND", "ID", "SEMICOLON",
      "INT_TYPE", "ID", "EQUAL", "FIRST", "ID", "SEMICOLON", "PAIR_TYPE",
      "LPAREN", "INT_TYPE", "COMMA", "PAIR_TYPE", "RPAREN", "ID", "EQUAL",
      "NULL", "SEMICOLON", "IF", "ID", "LESS_THAN", "ID", "THEN", "ID",
      "EQUAL", "FIRST", "ID", "SEMICOLON", "FIRST", "ID", "EQUAL", "CALL",
      "ID", "LPAREN", "ID", "COMMA", "ID", "RPAREN", "ELSE", "ID", "EQUAL",
      "SECOND", "ID", "SEMICOLON", "SECOND", "ID", "EQUAL", "CALL", "ID",
      "LPAREN", "ID", "COMMA", "ID", "RPAREN", "FI", "FI", "SEMICOLON",
      "RETURN", "ID", "END", "INT_TYPE", "ID", "LPAREN", "PAIR_TYPE",
      "LPAREN", "INT_TYPE", "COMMA", "PAIR_TYPE", "RPAREN", "ID", "RPAREN",
      "IS", "IF", "ID", "DOUBLE_EQUAL", "NULL", "THEN", "RETURN",
      "INT_LITERAL", "ELSE", "PAIR_TYPE", "LPAREN", "PAIR_TYPE", "COMMA",
      "PAIR_TYPE", "RPAREN", "ID", "EQUAL", "SECOND", "ID", "SEMICOLON",
      "PAIR_TYPE", "LPAREN", "INT_TYPE", "COMMA", "PAIR_TYPE", "RPAREN",
      "ID", "EQUAL", "FIRST", "ID", "SEMICOLON", "INT_TYPE", "ID", "EQUAL",
      "CALL", "ID", "LPAREN", "ID", "RPAREN", "SEMICOLON", "ID", "EQUAL",
      "FIRST", "ID", "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINT",
      "CHAR_LITERAL", "SEMICOLON", "ID", "EQUAL", "SECOND", "ID",
      "SEMICOLON", "ID", "EQUAL", "CALL", "ID", "LPAREN", "ID", "RPAREN",
      "SEMICOLON", "RETURN", "INT_LITERAL", "FI", "END", "INT_TYPE", "ID",
      "EQUAL", "INT_LITERAL", "SEMICOLON", "PRINT", "STR_LITERAL",
      "SEMICOLON", "READ", "ID", "SEMICOLON", "PRINT", "STR_LITERAL",
      "SEMICOLON", "PRINT", "ID", "SEMICOLON", "PRINTLN", "STR_LITERAL",
      "SEMICOLON", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
      "PAIR_TYPE", "LPAREN", "INT_TYPE", "COMMA", "PAIR_TYPE", "RPAREN",
      "ID", "EQUAL", "NULL", "SEMICOLON", "WHILE", "ID", "LESS_THAN", "ID",
      "DO", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "PRINT",
      "STR_LITERAL", "SEMICOLON", "PRINT", "ID", "PLUS", "INT_LITERAL",
      "SEMICOLON", "PRINT", "STR_LITERAL", "SEMICOLON", "READ", "ID",
      "SEMICOLON", "ID", "EQUAL", "CALL", "ID", "LPAREN", "ID", "COMMA",
      "ID", "RPAREN", "SEMICOLON", "ID", "EQUAL", "ID", "PLUS",
      "INT_LITERAL", "DONE", "SEMICOLON", "PRINT", "STR_LITERAL",
      "SEMICOLON", "ID", "EQUAL", "CALL", "ID", "LPAREN", "ID", "RPAREN",
      "SEMICOLON", "PRINTLN", "STR_LITERAL", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }

  "lexing with no semicolons" should "work" in {
    val file = synatxErr + "statements/noSemicolons.wacc"
    val expectedTokens: Array[String]
    = Array("BEGIN", "SKIP_", "SKIP_", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }

  "lexing with all semicolons" should "work" in {
    val file = synatxErr + "statements/allSemicolons.wacc"
    val expectedTokens: Array[String]
    = Array("BEGIN", "SEMICOLON", "SKIP_", "SEMICOLON", "SKIP_", "SEMICOLON",
      "END", "SEMICOLON", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }


  //    failing tests
  "lexing 'beginning'" should "give us the token ID not BEGIN" in {
    val file = synatxErr + "basic/beginning.wacc"

    val expectedTokens: Array[String] = Array("ID", "SKIP_", "SEMICOLON",
      "SKIP_", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)

  }

  "lexing '1begin'" should "give us the token INT_LITERAL as well as BEGIN" in {
    val file = synatxErr + "statements/startWithNumberTest.wacc"

    val expectedTokens: Array[String] = Array("INT_LITERAL", "BEGIN", "SKIP_",
      "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)

  }

  "lexing 'beg1n'" should "give us the token ID" in {
    val file = synatxErr + "statements/beg1n.wacc"

    val expectedTokens: Array[String] = Array("ID", "SKIP_",
      "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)

  }

  "lexing 'Begin' with an uppercase" should "give the token ID not BEGIN" in {
    val file = synatxErr + "basic/Begin.wacc"

    val expectedTokens: Array[String] = Array("ID", "SKIP_", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)

  }

  "lexing a bad comments" should "match several ID tokens" in {
    val files = Array(
      synatxErr + "basic/badComment.wacc",
      synatxErr + "basic/badComment2.wacc")

    val expectedTokens: Array[Array[String]] = Array(
      Array("BEGIN", "ID", "COMMA", "ID", "IS", "ID", "ID", "ID", "ID", "ID",
        "PRINT", "STR_LITERAL", "END", "EOF"),

      Array("BEGIN", "INT_TYPE", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON",
        "ID", "ID", "ID", "ID", "MINUS", "ID", "ID", "ID", "ID",
        "CHAR_LITERAL", "ID", "EQUAL", "INT_LITERAL", "SEMICOLON", "EXIT",
        "ID", "END", "EOF"))

    (files zip expectedTokens).map { case (file, tokens) =>
      assert(new LexerParserTemplate(file).getLexerResult === tokens)
    }

  }

  //  TODO: Is this working correctly?
  "lexing an unescaped character" should "match the character token rather " +
    "than a CHAR_LITERAL" in {
    val file = synatxErr + "basic/unescapedChar.wacc"

    val expectedTokens: Array[String] = Array("")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }

  "lexing an operator with a missing operand" should "match INT_LITERAL, " +
    "MINUS tokens" in {
    val file = synatxErr + "expressions/missingOperand2.wacc"

    val expectedTokens: Array[String] = Array("BEGIN", "INT_TYPE", "ID",
      "EQUAL", "INT_LITERAL", "MINUS", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }

  "lexing intf()" should "match ID" in {
    val file = synatxErr + "function/badlyNamed.wacc"

    val expectedTokens: Array[String] = Array("BEGIN", "ID", "LPAREN", "RPAREN",
      "IS", "RETURN", "INT_LITERAL", "END", "INT_TYPE", "ID", "EQUAL", "CALL",
      "ID", "LPAREN", "RPAREN", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }

  "lexing c-style pointers" should "match MULTIPLY's" in {
    val file = synatxErr + "function/thisIsNotC.wacc"

    val expectedTokens: Array[String] = Array("BEGIN", "INT_TYPE", "ID",
      "LPAREN", "INT_TYPE", "MULTIPLY", "ID", "RPAREN", "IS", "MULTIPLY",
      "ID", "EQUAL", "MULTIPLY", "ID", "PLUS", "INT_LITERAL", "SEMICOLON",
      "RETURN", "INT_LITERAL", "END", "INT_TYPE", "MULTIPLY", "ID", "EQUAL",
      "ID", "LPAREN", "INT_LITERAL", "RPAREN", "SEMICOLON", "ID", "LBRACKET",
      "INT_LITERAL", "RBRACKET", "EQUAL", "INT_LITERAL", "SEMICOLON",
      "INT_TYPE", "ID", "EQUAL", "CALL", "ID", "LPAREN", "ID", "RPAREN",
      "SEMICOLON", "PRINTLN", "MULTIPLY", "LPAREN", "ID", "RPAREN",
      "SEMICOLON", "FREE", "ID", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }

  "lexing ifi" should "match ID rather than IF" in {
    val file = synatxErr + "if/ifiErr.wacc"

    val expectedTokens: Array[String] = Array("BEGIN", "ID", "TRUE_LITERAL",
      "THEN", "SKIP_", "ELSE", "SKIP_", "FI", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }

  "lexing bad int assignments" should "1)match two INT_LITERAL's" +
    " 2) match INT_LITERAL + ID" in {
    val file = synatxErr + "variables/badintAssignments.wacc"

    val expectedTokens: Array[String] = Array("BEGIN", "INT_TYPE", "ID",
      "EQUAL", "INT_LITERAL", "INT_LITERAL", "SEMICOLON", "INT_TYPE", "ID",
      "EQUAL", "INT_LITERAL", "ID", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }

  "lexing doo and dono" should "both match ID" in {
    val file = synatxErr + "while/dooErr.wacc"

    val expectedTokens: Array[String] = Array("BEGIN", "WHILE",
      "FALSE_LITERAL", "ID", "SKIP_", "ID", "END", "EOF")

    assert(new LexerParserTemplate(file).getLexerResult === expectedTokens)
  }

}
