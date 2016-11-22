// package errorLogging

object SemanticErrorLog {
  var semanticErrorLog = Seq[String]()

  def add(s: String): Unit = {
    semanticErrorLog = semanticErrorLog :+ s
  }

  def getNumErrors(): Int = {
    semanticErrorLog.size
  }

  def getErrors(): Seq[String] = {
    semanticErrorLog
  }

  def printErrors(): Unit = {
    println(semanticErrorLog)
  }
}
