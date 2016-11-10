
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

//Ruby generated test suite. To check WACCVisitor.
//Created at: 10/11/2016 19:12.

public class WACCVisitorTest {
  

  @Test
  public void testbadLookup01() {
//      String str = "src/test/test_files/invalidSyntax/pairs/badLookup01.wacc";
      String str = "wacc_examples/invalid/syntaxErr/pairs/badLookup01.wacc";
      assertThat(Main.compile(str), is(100));
  }

//  @Test
//  public void testbadLookup02() {
//      String str =                 ("src/test/test_files/invalidSyntax/pairs/badLookup02.wacc");
//      assertThat(Main.compile(str), is(100));
//  }
//
//  @Test
//  public void testdoubleSeq() {
//            String str = "src/test/test_files/invalidSyntax/sequence/doubleSeq.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testemptySeq() {
//            String str = "src/test/test_files/invalidSyntax/sequence/emptySeq.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testendSeq() {
//            String str = "src/test/test_files/invalidSyntax/sequence/endSeq.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testextraSeq() {
//            String str = "src/test/test_files/invalidSyntax/sequence/extraSeq.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testmissingSeq() {
//            String str = "src/test/test_files/invalidSyntax/sequence/missingSeq.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testallSemicolons() {
//            String str = "src/test/test_files/invalidSyntax/statements/allSemicolons.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testnoSemicolons() {
//            String str = "src/test/test_files/invalidSyntax/statements/noSemicolons.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void teststartWithNumberTest() {
//            String str = "src/test/test_files/invalidSyntax/statements/startWithNumberTest.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testarrayExpr() {
//            String str = "src/test/test_files/invalidSyntax/array/arrayExpr.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testbadintAssignments() {
//            String str = "src/test/test_files/invalidSyntax/variables/badintAssignments.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testbigIntAssignment() {
//            String str = "src/test/test_files/invalidSyntax/variables/bigIntAssignment.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testvarNoName() {
//            String str = "src/test/test_files/invalidSyntax/variables/varNoName.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testBegin() {
//            String str = "src/test/test_files/invalidSyntax/basic/Begin.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testbadComment() {
//            String str = "src/test/test_files/invalidSyntax/basic/badComment.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testbadComment2() {
//            String str = "src/test/test_files/invalidSyntax/basic/badComment2.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testbadEscape() {
//            String str = "src/test/test_files/invalidSyntax/basic/badEscape.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testbeginNoend() {
//            String str = "src/test/test_files/invalidSyntax/basic/beginNoend.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testbeginning() {
//            String str = "src/test/test_files/invalidSyntax/basic/beginning.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testbgnErr() {
//            String str = "src/test/test_files/invalidSyntax/basic/bgnErr.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//    @Test
//  public void testmultipleBegins() {
//            String str = "src/test/test_files/invalidSyntax/basic/multipleBegins.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testnoBody() {
//            String str = "src/test/test_files/invalidSyntax/basic/noBody.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testskpErr() {
//            String str = "src/test/test_files/invalidSyntax/basic/skpErr.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testunescapedChar() {
//            String str = "src/test/test_files/invalidSyntax/basic/unescapedChar.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testdooErr() {
//            String str = "src/test/test_files/invalidSyntax/while/dooErr.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testdonoErr() {
//            String str = "src/test/test_files/invalidSyntax/while/donoErr.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testwhilErr() {
//            String str = "src/test/test_files/invalidSyntax/while/whilErr.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testwhileNodo() {
//            String str = "src/test/test_files/invalidSyntax/while/whileNodo.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testwhileNodone() {
//            String str = "src/test/test_files/invalidSyntax/while/whileNodone.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testmissingOperand1() {
//            String str = "src/test/test_files/invalidSyntax/expressions/missingOperand1.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testmissingOperand2() {
//            String str = "src/test/test_files/invalidSyntax/expressions/missingOperand2.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testprintlnConcat() {
//            String str = "src/test/test_files/invalidSyntax/expressions/printlnConcat.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testthisIsNotC() {
//            String str = "src/test/test_files/invalidSyntax/function/thisIsNotC.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testbadlyNamed() {
//            String str = "src/test/test_files/invalidSyntax/function/badlyNamed.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testbadlyPlaced() {
//            String str = "src/test/test_files/invalidSyntax/function/badlyPlaced.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfuncExpr() {
//            String str = "src/test/test_files/invalidSyntax/function/funcExpr.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfuncExpr2() {
//            String str = "src/test/test_files/invalidSyntax/function/funcExpr2.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfunctionConditionalNoReturn() {
//            String str = "src/test/test_files/invalidSyntax/function/functionConditionalNoReturn.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfunctionJunkAfterReturn() {
//            String str = "src/test/test_files/invalidSyntax/function/functionJunkAfterReturn.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfunctionLateDefine() {
//            String str = "src/test/test_files/invalidSyntax/function/functionLateDefine.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfunctionMissingCall() {
//            String str = "src/test/test_files/invalidSyntax/function/functionMissingCall.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfunctionMissingPType() {
//            String str = "src/test/test_files/invalidSyntax/function/functionMissingPType.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfunctionMissingParam() {
//            String str = "src/test/test_files/invalidSyntax/function/functionMissingParam.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfunctionMissingType() {
//            String str = "src/test/test_files/invalidSyntax/function/functionMissingType.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfunctionNoReturn() {
//            String str = "src/test/test_files/invalidSyntax/function/functionNoReturn.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfunctionScopeDef() {
//            String str = "src/test/test_files/invalidSyntax/function/functionScopeDef.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testmutualRecursionNoReturn() {
//            String str = "src/test/test_files/invalidSyntax/function/mutualRecursionNoReturn.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testnoBodyAfterFuncs() {
//            String str = "src/test/test_files/invalidSyntax/function/noBodyAfterFuncs.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testifNoelse() {
//            String str = "src/test/test_files/invalidSyntax/if/ifNoelse.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testifNofi() {
//            String str = "src/test/test_files/invalidSyntax/if/ifNofi.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testifNothen() {
//            String str = "src/test/test_files/invalidSyntax/if/ifNothen.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testifiErr() {
//            String str = "src/test/test_files/invalidSyntax/if/ifiErr.wacc";
//        assertThat(Main.compile(str), is(100));
//
//  }
//
//  @Test
//  public void testfreeNonPair() {
//            String str = "src/test/test_files/invalidSemantic/pairs/freeNonPair.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testprintTypeErr01() {
//            String str = "src/test/test_files/invalidSemantic/print/printTypeErr01.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testreadTypeErr01() {
//            String str = "src/test/test_files/invalidSemantic/read/readTypeErr01.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testreadTypeErr() {
//            String str = "src/test/test_files/invalidSemantic/IO/readTypeErr.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbadScopeRedefine() {
//            String str = "src/test/test_files/invalidSemantic/scope/badScopeRedefine.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbadCharExit() {
//            String str = "src/test/test_files/invalidSemantic/exit/badCharExit.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testexitNonInt() {
//            String str = "src/test/test_files/invalidSemantic/exit/exitNonInt.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testglobalReturn() {
//            String str = "src/test/test_files/invalidSemantic/exit/globalReturn.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testboolOpTypeErr() {
//            String str = "src/test/test_files/invalidSemantic/expressions/boolOpTypeErr.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testexprTypeErr() {
//            String str = "src/test/test_files/invalidSemantic/expressions/exprTypeErr.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testintOpTypeErr() {
//            String str = "src/test/test_files/invalidSemantic/expressions/intOpTypeErr.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testlessPairExpr() {
//            String str = "src/test/test_files/invalidSemantic/expressions/lessPairExpr.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testmixedOpTypeErr() {
//            String str = "src/test/test_files/invalidSemantic/expressions/mixedOpTypeErr.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testmoreArrExpr() {
//            String str = "src/test/test_files/invalidSemantic/expressions/moreArrExpr.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr10() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr10.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr11() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr11.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr12() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr12.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testcaseMatters() {
//            String str = "src/test/test_files/invalidSemantic/variables/caseMatters.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testdoubleDeclare() {
//            String str = "src/test/test_files/invalidSemantic/variables/doubleDeclare.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testundeclaredScopeVar() {
//            String str = "src/test/test_files/invalidSemantic/variables/undeclaredScopeVar.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testundeclaredVar() {
//            String str = "src/test/test_files/invalidSemantic/variables/undeclaredVar.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testundeclaredVarAccess() {
//            String str = "src/test/test_files/invalidSemantic/variables/undeclaredVarAccess.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr01() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr01.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr02() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr02.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr03() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr03.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr04() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr04.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr05() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr05.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr06() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr06.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr07() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr07.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr08() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr08.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testbasicTypeErr09() {
//            String str = "src/test/test_files/invalidSemantic/variables/basicTypeErr09.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfalsErr() {
//            String str = "src/test/test_files/invalidSemantic/while/falsErr.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testtruErr() {
//            String str = "src/test/test_files/invalidSemantic/while/truErr.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testwhileIntCondition() {
//            String str = "src/test/test_files/invalidSemantic/while/whileIntCondition.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfuncVarAccess() {
//            String str = "src/test/test_files/invalidSemantic/function/funcVarAccess.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfunctionAssign() {
//            String str = "src/test/test_files/invalidSemantic/function/functionAssign.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfunctionBadArgUse() {
//            String str = "src/test/test_files/invalidSemantic/function/functionBadArgUse.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfunctionBadCall() {
//            String str = "src/test/test_files/invalidSemantic/function/functionBadCall.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfunctionBadParam() {
//            String str = "src/test/test_files/invalidSemantic/function/functionBadParam.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfunctionBadReturn() {
//            String str = "src/test/test_files/invalidSemantic/function/functionBadReturn.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfunctionOverArgs() {
//            String str = "src/test/test_files/invalidSemantic/function/functionOverArgs.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfunctionRedefine() {
//            String str = "src/test/test_files/invalidSemantic/function/functionRedefine.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfunctionSwapArgs() {
//            String str = "src/test/test_files/invalidSemantic/function/functionSwapArgs.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfunctionUnderArgs() {
//            String str = "src/test/test_files/invalidSemantic/function/functionUnderArgs.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testifIntCondition() {
//            String str = "src/test/test_files/invalidSemantic/if/ifIntCondition.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testfuncMess() {
//            String str = "src/test/test_files/invalidSemantic/multiple/funcMess.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testifAndWhileErrs() {
//            String str = "src/test/test_files/invalidSemantic/multiple/ifAndWhileErrs.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testmessyExpr() {
//            String str = "src/test/test_files/invalidSemantic/multiple/messyExpr.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testmultiCaseSensitivity() {
//            String str = "src/test/test_files/invalidSemantic/multiple/multiCaseSensitivity.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
//
//  @Test
//  public void testmultiTypeErrs() {
//            String str = "src/test/test_files/invalidSemantic/multiple/multiTypeErrs.wacc";
//        assertThat(Main.compile(str), is(200));
//
//  }
}
