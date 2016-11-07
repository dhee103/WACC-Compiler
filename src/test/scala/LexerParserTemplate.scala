class LexerParserTemplate(val filename: String){

  var waccLex = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

  // Get a list of matched tokens
  var tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLex)

  val tokenIDs : Array[String] =  waccLex.getRuleNames

  tokens.fill()

  private def mapToId(typeNum: Int, tokenNames: Array[String]): String = {
    if (typeNum > 0) tokenNames(typeNum - 1) else "EOF"
  }

  private def constructTypeNumberArrays(xs : org.antlr.v4.runtime.CommonTokenStream): Array[Int] = {

    val types: Array[Int] = new Array[Int](xs.size())
    for(i <- types.indices){
      types(i) = tokens.get(i).getType
    }
    types

  }

  private def ConstructTokenStringArray(xs : Array[Int]): Array[String] = {
      xs.map(mapToId(_, tokenIDs))
  }

  def getLexerResult : Array[String] = {
    val typeList = constructTypeNumberArrays(tokens)
    val tokenList = ConstructTokenStringArray(typeList)
    tokenList
  }

}
