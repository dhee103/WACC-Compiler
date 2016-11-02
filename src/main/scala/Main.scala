import org.antlr.v4.runtime.ANTLRFileStream._
import org.antlr.v4.runtime.CommonTokenStream._
import org.antlr.v4.runtime.Token._

object Main {

  def main(args : Array[String]): Unit = {

    println("What is the name of your file?")

    var filename = scala.io.StdIn.readLine()

    var waccLex = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream(filename))

    // Get a list of matched tokens
    var tokens = new org.antlr.v4.runtime.CommonTokenStream(waccLex)

    println("The tokens are " + tokens.getText())

    println("tokens size is " + tokens.size())

    for (i <- 0 until tokens.size()) {
      println("token " + i  + " is " + tokens.get(i).getText() + " of type " + tokens.get(i).getType())
    }

  }

  def factorial(n: Int): Int = n match {
    case 0 => 1
    case _ => n * factorial(n - 1)
  }

}