// package errorLogging

trait ErrorLog {
  val errorType: String
  var log = Seq[String]()

  def add(s: String): Unit = {
    log = log :+ s
  }

  def numErrors: Int = {
    log.size
  }

  def printErrors(): Unit = {
    log.map(Console.RED ++ Console.BOLD ++ s"[$errorType Error] " ++ Console.RESET ++ _).map(println _)
    println(s"$numErrors found.")
  }
}

object SemanticErrorLog extends ErrorLog {
  override val errorType = "Semantic"
}

object SyntaxErrorLog extends ErrorLog {
  override val errorType = "Syntax"
}
