import WaccLexer._
import org.antlr.v4.runtime.ANTLRFileStream._
import org.antlr.v4.runtime.CommonTokenStream._
import org.antlr.v4.runtime.Token._

object Main {

def main(args : Array[String]): Unit = {

  var WaccLex = new WaccLexer(new org.antlr.v4.runtime.ANTLRFileStream("Code.wacc") )

  // Get a list of matched tokens
   var tokens = new org.antlr.v4.runtime.CommonTokenStream(WaccLex);

   println("The tokens are " + tokens.getText());

   println("tokens size is " + tokens.size())

   for (i <- 0 to tokens.size() - 1) {

     println("token " + i  + " is " + tokens.get(i).getText() + " of type " + tokens.get(i).getType())

   }

  }
}
