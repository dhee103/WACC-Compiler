import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * Created by dsg115 on 10/11/16.
 */
public class DescriptiveErrorListener extends BaseErrorListener {
    public static DescriptiveErrorListener INSTANCE = new DescriptiveErrorListener();
//    public static boolean errorFlag = false;

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) {

//        if (!REPORT_SYNTAX_ERRORS) {
//            return;
//        }

//        System.out.println("there are errors");

        String sourceName = recognizer.getInputStream().getSourceName();
        if (!sourceName.isEmpty()) {
//            errorFlag = true;
            sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
        }

        System.err.println(sourceName + "line " + line + ":" + charPositionInLine + " " + msg);
    }

//    public boolean isError() {
//        return errorFlag;
//    }



}


