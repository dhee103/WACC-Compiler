

object Main {

  def main(args : Array[String]): Unit = {

    println("What is the name of your file?")

    var filename = scala.io.StdIn.readLine()

    var waccLex = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

    // Get a list of matched tokens
    var tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLex)

    val tokenIDs : Array[String] =  waccLex.getRuleNames()

    println("The tokens are " + tokens.getText())

    println("tokens size is " + tokens.size())

    for (i <- 0 until tokens.size()) {
      println("token " + i  + " is " + tokens.get(i).getText() + " of type " + mapToId(tokens.get(i).getType(), tokenIDs))
    }

  }

  def factorial(n: Int): Int = n match {
    case 0 => 1
    case _ => n * factorial(n - 1)
  }

  def mapToId(typeNum: Int, tokenNames: Array[String]): String = {
    if (typeNum > 0) tokenNames(typeNum - 1) else "EOF"
  }

}