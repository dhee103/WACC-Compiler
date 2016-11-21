import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer

class DescriptiveErrorListener extends BaseErrorListener {

  override def syntaxError(recognizer: Recognizer[_, _],
      offendingSymbol: AnyRef,
      line: Int,
      charPositionInLine: Int,
      msg: String,
      e: RecognitionException) {

    // var sourceName = recognizer.getInputStream.getSourceName
    // if (!sourceName.isEmpty) {
    //   sourceName = "%s:%d:%d: ".format(sourceName, line, charPositionInLine)
    // }
    System.err.println("[SyntaxError] line " + line + ":" + charPositionInLine + " " + msg)
  }
}
