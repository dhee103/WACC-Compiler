import org.scalatest.{FlatSpec, Matchers}

//Ruby generated test suite. To check WACCVisitor.
//Created at: 10/11/2016 19:12.
class WACCVisTest extends FlatSpec with Matchers {
  " testbadLookup01()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/pairs/badLookup01.wacc"
    assert(Main.compile(str) === 100)
  }


  "testbadLookup02()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/pairs/badLookup02.wacc"

    assert(Main.compile(str) === 100)
  }


  "testdoubleSeq()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/sequence/doubleSeq.wacc"
    assert(Main.compile(str) === 100)

  }


  "testemptySeq()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/sequence/emptySeq.wacc"
    assert(Main.compile(str) === 100)

  }


  "testendSeq()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/sequence/endSeq.wacc"
    assert(Main.compile(str) === 100)

  }


  "testextraSeq()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/sequence/extraSeq.wacc"
    assert(Main.compile(str) === 100)

  }


  "testmissingSeq()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/sequence/missingSeq.wacc"
    assert(Main.compile(str) === 100)

  }


  "testallSemicolons()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/statements" +
      "/allSemicolons.wacc"
    assert(Main.compile(str) === 100)

  }


  "testnoSemicolons()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/statements" +
      "/noSemicolons.wacc"
    assert(Main.compile(str) === 100)

  }


  "teststartWithNumberTest()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/statements" +
      "/startWithNumberTest.wacc"
    assert(Main.compile(str) === 100)

  }


  "testarrayExpr()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/array/arrayExpr.wacc"
    assert(Main.compile(str) === 100)

  }


  "testbadintAssignments()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/variables" +
      "/badintAssignments.wacc"
    assert(Main.compile(str) === 100)

  }


//  "testbigIntAssignment()" should "work" in {
//    val str: String = "wacc_examples/invalid/syntaxErr/variables" +
//      "/bigIntAssignment.wacc"
//    assert(Main.compile(str) === 100)
//
//  }


  "testvarNoName()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/variables/varNoName.wacc"
    assert(Main.compile(str) === 100)

  }


  "testBegin()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/basic/Begin.wacc"
    assert(Main.compile(str) === 100)

  }


  "testbadComment()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/basic/badComment.wacc"
    assert(Main.compile(str) === 100)

  }


  "testbadComment2()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/basic/badComment2.wacc"
    assert(Main.compile(str) === 100)

  }


  "testbadEscape()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/basic/badEscape.wacc"
    assert(Main.compile(str) === 100)

  }


  "testbeginNoend()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/basic/beginNoend.wacc"
    assert(Main.compile(str) === 100)

  }


  "testbeginning()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/basic/beginning.wacc"
    assert(Main.compile(str) === 100)

  }


  "testbgnErr()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/basic/bgnErr.wacc"
    assert(Main.compile(str) === 100)

  }


  "testmultipleBegins()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/basic/multipleBegins.wacc"
    assert(Main.compile(str) === 100)

  }


  "testnoBody()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/basic/noBody.wacc"
    assert(Main.compile(str) === 100)

  }


  "testskpErr()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/basic/skpErr.wacc"
    assert(Main.compile(str) === 100)

  }


  "testunescapedChar()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/basic/unescapedChar.wacc"
    assert(Main.compile(str) === 100)

  }


  "testdooErr()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/while/dooErr.wacc"
    assert(Main.compile(str) === 100)

  }


  "testdonoErr()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/while/donoErr.wacc"
    assert(Main.compile(str) === 100)

  }


  "testwhilErr()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/while/whilErr.wacc"
    assert(Main.compile(str) === 100)

  }


  "testwhileNodo()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/while/whileNodo.wacc"
    assert(Main.compile(str) === 100)

  }


  "testwhileNodone()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/while/whileNodone.wacc"
    assert(Main.compile(str) === 100)

  }


  "testmissingOperand1()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/expressions" +
      "/missingOperand1.wacc"
    assert(Main.compile(str) === 100)

  }


  "testmissingOperand2()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/expressions" +
      "/missingOperand2.wacc"
    assert(Main.compile(str) === 100)

  }


  "testprintlnConcat()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/expressions" +
      "/printlnConcat.wacc"
    assert(Main.compile(str) === 100)

  }


  "testthisIsNotC()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/thisIsNotC.wacc"
    assert(Main.compile(str) === 100)

  }


  "testbadlyNamed()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/badlyNamed.wacc"
    assert(Main.compile(str) === 100)

  }


  "testbadlyPlaced()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/badlyPlaced.wacc"
    assert(Main.compile(str) === 100)

  }


  "testfuncExpr()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/funcExpr.wacc"
    assert(Main.compile(str) === 100)

  }


  "testfuncExpr2()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/funcExpr2.wacc"
    assert(Main.compile(str) === 100)

  }


//  "testfunctionConditionalNoReturn()" should "work" in {
//    val str: String =
//      "wacc_examples/invalid/syntaxErr/function/functionConditionalNoReturn" +
//        ".wacc"
//    assert(Main.compile(str) === 100)
//
//  }


//  "testfunctionJunkAfterReturn()" should "work" in {
//    val str: String =
//      "wacc_examples/invalid/syntaxErr/function/functionJunkAfterReturn.wacc"
//    assert(Main.compile(str) === 100)
//
//  }


  "testfunctionLateDefine()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/functionLateDefine.wacc"
    assert(Main.compile(str) === 100)

  }


  "testfunctionMissingCall()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/functionMissingCall.wacc"
    assert(Main.compile(str) === 100)

  }


  "testfunctionMissingPType()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/functionMissingPType.wacc"
    assert(Main.compile(str) === 100)

  }


  "testfunctionMissingParam()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/functionMissingParam.wacc"
    assert(Main.compile(str) === 100)

  }


  "testfunctionMissingType()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/functionMissingType.wacc"
    assert(Main.compile(str) === 100)

  }


//  "testfunctionNoReturn()" should "work" in {
//    val str: String =
//      "wacc_examples/invalid/syntaxErr/function/functionNoReturn.wacc"
//    assert(Main.compile(str) === 100)
//
//  }


  "testfunctionScopeDef()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/functionScopeDef.wacc"
    assert(Main.compile(str) === 100)

  }


//  "testmutualRecursionNoReturn()" should "work" in {
//    val str: String =
//      "wacc_examples/invalid/syntaxErr/function/mutualRecursionNoReturn.wacc"
//    assert(Main.compile(str) === 100)
//
//  }


  "testnoBodyAfterFuncs()" should "work" in {
    val str: String =
      "wacc_examples/invalid/syntaxErr/function/noBodyAfterFuncs.wacc"
    assert(Main.compile(str) === 100)

  }


  "testifNoelse()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/if/ifNoelse.wacc"
    assert(Main.compile(str) === 100)

  }


  "testifNofi()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/if/ifNofi" +
      ".wacc"
    assert(Main.compile(str) === 100)

  }


  "testifNothen()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/if/ifNothen.wacc"
    assert(Main.compile(str) === 100)

  }


  "testifiErr()" should "work" in {
    val str: String = "wacc_examples/invalid/syntaxErr/if/ifiErr" +
      ".wacc"
    assert(Main.compile(str) === 100)

  }


  "testfreeNonPair()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/pairs/freeNonPair.wacc"
    assert(Main.compile(str) === 200)

  }


  "testprintTypeErr01()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/print/printTypeErr01.wacc"
    assert(Main.compile(str) === 200)

  }


  "testreadTypeErr01()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/read/readTypeErr01.wacc"
    assert(Main.compile(str) === 200)

  }


  "testreadTypeErr()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/IO/readTypeErr.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbadScopeRedefine()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/scope/badScopeRedefine.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbadCharExit()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/exit/badCharExit.wacc"
    assert(Main.compile(str) === 200)

  }


  "testexitNonInt()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/exit/exitNonInt.wacc"
    assert(Main.compile(str) === 200)

  }


  "testglobalReturn()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/exit/globalReturn.wacc"
    assert(Main.compile(str) === 200)

  }


  "testboolOpTypeErr()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/expressions" +
      "/boolOpTypeErr.wacc"
    assert(Main.compile(str) === 200)

  }


  "testexprTypeErr()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/expressions" +
      "/exprTypeErr.wacc"
    assert(Main.compile(str) === 200)

  }


  "testintOpTypeErr()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/expressions" +
      "/intOpTypeErr.wacc"
    assert(Main.compile(str) === 200)

  }


  "testlessPairExpr()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/expressions" +
      "/lessPairExpr.wacc"
    assert(Main.compile(str) === 200)

  }


  "testmixedOpTypeErr()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/expressions" +
      "/mixedOpTypeErr.wacc"
    assert(Main.compile(str) === 200)

  }


  "testmoreArrExpr()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/expressions" +
      "/moreArrExpr.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr10()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr10.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr11()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr11.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr12()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr12.wacc"
    assert(Main.compile(str) === 200)

  }


  "testcaseMatters()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/caseMatters.wacc"
    assert(Main.compile(str) === 200)

  }


  "testdoubleDeclare()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/doubleDeclare.wacc"
    assert(Main.compile(str) === 200)

  }


  "testundeclaredScopeVar()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/undeclaredScopeVar.wacc"
    assert(Main.compile(str) === 200)

  }


  "testundeclaredVar()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/undeclaredVar.wacc"
    assert(Main.compile(str) === 200)

  }


  "testundeclaredVarAccess()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/undeclaredVarAccess.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr01()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr01.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr02()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr02.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr03()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr03.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr04()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr04.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr05()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr05.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr06()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr06.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr07()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr07.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr08()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr08.wacc"
    assert(Main.compile(str) === 200)

  }


  "testbasicTypeErr09()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/variables" +
      "/basicTypeErr09.wacc"
    assert(Main.compile(str) === 200)

  }


  "testfalsErr()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/while/falsErr.wacc"
    assert(Main.compile(str) === 200)

  }


  "testtruErr()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/while/truErr.wacc"
    assert(Main.compile(str) === 200)

  }


  "testwhileIntCondition()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/while/whileIntCondition.wacc"
    assert(Main.compile(str) === 200)

  }


  "testfuncVarAccess()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/function" +
      "/funcVarAccess" +
      ".wacc"
    assert(Main.compile(str) === 200)

  }


  "testfunctionAssign()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/function" +
      "/functionAssign.wacc"
    assert(Main.compile(str) === 200)

  }


  "testfunctionBadArgUse()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/function" +
      "/functionBadArgUse.wacc"
    assert(Main.compile(str) === 200)

  }


  "testfunctionBadCall()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/function" +
      "/functionBadCall.wacc"
    assert(Main.compile(str) === 200)

  }


  "testfunctionBadParam()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/function" +
      "/functionBadParam.wacc"
    assert(Main.compile(str) === 200)

  }


  "testfunctionBadReturn()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/function" +
      "/functionBadReturn.wacc"
    assert(Main.compile(str) === 200)

  }


  "testfunctionOverArgs()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/function" +
      "/functionOverArgs.wacc"
    assert(Main.compile(str) === 200)

  }


  "testfunctionRedefine()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/function" +
      "/functionRedefine.wacc"
    assert(Main.compile(str) === 200)

  }


  "testfunctionSwapArgs()" should "work" in {
    val str: String =
      "wacc_examples/invalid/semanticErr/function/functionSwapArgs.wacc"
    assert(Main.compile(str) === 200)

  }


  "testfunctionUnderArgs()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/function" +
      "/functionUnderArgs.wacc"
    assert(Main.compile(str) === 200)

  }


  "testifIntCondition()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/if" +
      "/ifIntCondition" +
      ".wacc"
    assert(Main.compile(str) === 200)

  }


  "testfuncMess()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/multiple" +
      "/funcMess" +
      ".wacc"
    assert(Main.compile(str) === 200)

  }


  "testifAndWhileErrs()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/multiple" +
      "/ifAndWhileErrs.wacc"
    assert(Main.compile(str) === 200)

  }


  "testmessyExpr()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/multiple" +
      "/messyExpr" +
      ".wacc"
    assert(Main.compile(str) === 200)

  }


  "testmultiCaseSensitivity()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/multiple" +
      "/multiCaseSensitivity.wacc"
    assert(Main.compile(str) === 200)

  }


  "testmultiTypeErrs()" should "work" in {
    val str: String = "wacc_examples/invalid/semanticErr/multiple" +
      "/multiTypeErrs" +
      ".wacc"
    assert(Main.compile(str) === 200)

  }
}
