// package errorLogging

object SemanticErrorLog {
  var semanticErrorLog = Seq[String]()

  def add(s: String): Unit = {
    semanticErrorLog = semanticErrorLog :+ s
  }

  def getNumErrors: Int = {
    semanticErrorLog.size
  }

  def getErrors: Seq[String] = {
    semanticErrorLog
  }

  def printErrors(): Unit = {
    semanticErrorLog map (println _)
  }
}

object SyntaxErrorLog {
  var syntaxErrorLog = Seq[String]()

  def add(s: String): Unit = {
    syntaxErrorLog = syntaxErrorLog :+ s
  }

  def getNumErrors: Int = {
    syntaxErrorLog.size
  }

  def getErrors: Seq[String] = {
    syntaxErrorLog
  }

  def printErrors(): Unit = {
    syntaxErrorLog map (println _)
  }
}
