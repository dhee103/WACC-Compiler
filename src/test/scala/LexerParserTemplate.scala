class LexerParserTemplate(val filename: String){

  var waccLex = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

  var tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLex)

  val lexVocab = waccLex.getVocabulary()

  val waccParse = new WaccParser(tokens)

  val tree = waccParse.prog()

  tokens.fill()

  private def constructTokenStringArray(tokenStream : org.antlr.v4.runtime.CommonTokenStream): Array[String] = {

    val tokensArr: Array[String] = new Array[String](tokenStream.size())

    for(i <- tokensArr.indices) {

      tokensArr(i) = getTokenName(i)

    }
    tokensArr

  }

  private def getTokenName(index: Int): String
    = lexVocab.getSymbolicName(tokens.get(index).getType)

  def getLexerResult: Array[String] = {

    val typeList = constructTokenStringArray(tokens)
    typeList

  }

  def getParserResultAsTree: String
    = tree.toStringTree(waccParse)

}
