import WaccParser._

class AstBuildingVisitor extends WaccParserBaseVisitor[AstNode] {

  // for testing
  def currentMethodName(): String = Thread.currentThread().getStackTrace()(2)
    .getMethodName

  override def visitProg(ctx: WaccParser.ProgContext): AstNode = {
//    println("hit " + currentMethodName())
    val noOfChildren = ctx.getChildCount
    val statChild: StatNode = visit(ctx.getChild(noOfChildren - 3))
      .asInstanceOf[StatNode]

    val funcChildren: IndexedSeq[FuncNode] =
      for (i <- 1 until noOfChildren - 3) yield visit(ctx.getChild(i))
        .asInstanceOf[FuncNode]
    ProgNode(statChild, funcChildren)
  }

  override def visitFunc(ctx: WaccParser.FuncContext): AstNode = {
    // println("hit " + currentMethodName())
    val noOfChildren = ctx.getChildCount
    val returnType: TypeNode = visit(ctx.getChild(0)).asInstanceOf[TypeNode]
    val identChild: IdentNode = visit(ctx.getChild(1)).asInstanceOf[IdentNode]
    var paramChild: ParamListNode = ParamListNode(IndexedSeq())
    if (noOfChildren == 8) {
      paramChild = visit(ctx.getChild(3)).asInstanceOf[ParamListNode]
    }
    val statChild: StatNode = visit(ctx.getChild(noOfChildren - 2))
      .asInstanceOf[StatNode]

    if (!endsInReturnStatement(statChild)) {
      val funcName = identChild.name
      SyntaxErrorLog.add(s"Function $funcName does not end in return or exit statement.")
    }

    FuncNode(returnType, identChild, paramChild, statChild)

  }

  def endsInReturnStatement(statement: StatNode): Boolean = {

    statement match {
      case _: ReturnNode | _: ExitNode => true
      case stat: SequenceNode => endsInReturnStatement(stat.sndStat)
      case stat: IfNode => endsInReturnStatement(stat.thenStat) &&
        endsInReturnStatement(stat.elseStat)
      case stat: NewBeginNode => endsInReturnStatement(stat.body)
      case _ => false
    }
  }


  override def visitParam_list(ctx: WaccParser.Param_listContext): AstNode = {
    val noOfChildren = ctx.getChildCount
    val paramChildren: IndexedSeq[ParamNode] =
      for (i <- 0 until noOfChildren if i % 2 == 0)
        yield visit(ctx.getChild(i)).asInstanceOf[ParamNode]

    ParamListNode(paramChildren)
  }

  override def visitParam(ctx: WaccParser.ParamContext): AstNode = {
    val typeChild: TypeNode = visit(ctx.getChild(0)).asInstanceOf[TypeNode]
    val identChild: IdentNode = visit(ctx.getChild(1)).asInstanceOf[IdentNode]

    ParamNode(typeChild, identChild)
  }

  override def visitSkipStat(ctx: WaccParser.SkipStatContext): SkipStatNode = {
    SkipStatNode()
  }

  override def visitDeclaration(ctx: WaccParser.DeclarationContext):
  DeclarationNode = {
    val variableType: TypeNode = visit(ctx.getChild(0)).asInstanceOf[TypeNode]
    val identifier: IdentNode = visit(ctx.getChild(1)).asInstanceOf[IdentNode]
    val rhs: AssignmentRightNode = visit(ctx.getChild(3))
      .asInstanceOf[AssignmentRightNode]

    DeclarationNode(variableType, identifier, rhs)
  }

  override def visitAssignment(ctx: WaccParser.AssignmentContext):
  AssignmentNode = {
    val lhs: AssignmentLeftNode = visit(ctx.getChild(0))
      .asInstanceOf[AssignmentLeftNode]
    val rhs: AssignmentRightNode = visit(ctx.getChild(2))
      .asInstanceOf[AssignmentRightNode]

    AssignmentNode(lhs, rhs)
  }

  override def visitRead(ctx: WaccParser.ReadContext): ReadNode = {
    val variable: AssignmentLeftNode = visit(ctx.getChild(1))
      .asInstanceOf[AssignmentLeftNode]

    ReadNode(variable)
  }

  override def visitFree(ctx: WaccParser.FreeContext): FreeNode = {
    val variable: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    FreeNode(variable)
  }

  override def visitReturn(ctx: WaccParser.ReturnContext): ReturnNode = {
    val returnValue: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    ReturnNode(returnValue)
  }

  override def visitExit(ctx: WaccParser.ExitContext): ExitNode = {
    val exitCode: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    ExitNode(exitCode)
  }

  override def visitPrint(ctx: WaccParser.PrintContext): PrintNode = {
    //    println("hit " + currentMethodName())

    val text: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    PrintNode(text)
  }

  override def visitPrintln(ctx: WaccParser.PrintlnContext): PrintlnNode = {
    //    println("hit " + currentMethodName())

    val text: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    PrintlnNode(text)
  }

  override def visitIf(ctx: WaccParser.IfContext): IfNode = {
    //    println("hit " + currentMethodName())

    val condition: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]
    val thenStat: StatNode = visit(ctx.getChild(3)).asInstanceOf[StatNode]
    val elseStat: StatNode = visit(ctx.getChild(5)).asInstanceOf[StatNode]

    IfNode(condition, thenStat, elseStat)
  }

  override def visitWhile(ctx: WaccParser.WhileContext): WhileNode = {
    //    println("hit " + currentMethodName())

    val condition: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]
    val loopBody: StatNode = visit(ctx.getChild(3)).asInstanceOf[StatNode]

    WhileNode(condition, loopBody)
  }

  override def visitNewBegin(ctx: WaccParser.NewBeginContext): NewBeginNode = {
    //    println("hit " + currentMethodName())

    val body: StatNode = visit(ctx.getChild(1)).asInstanceOf[StatNode]

    NewBeginNode(body)
  }

  override def visitSequence(ctx: WaccParser.SequenceContext): SequenceNode = {
    //    println("hit " + currentMethodName())

    val fstStat: StatNode = visit(ctx.getChild(0)).asInstanceOf[StatNode]
    val sndStat: StatNode = visit(ctx.getChild(2)).asInstanceOf[StatNode]

    SequenceNode(fstStat, sndStat)
  }

  override def visitIdentLHS(ctx: WaccParser.IdentLHSContext):
  AssignmentLeftNode = {
    //    println("hit " + currentMethodName())

    visit(ctx.getChild(0)).asInstanceOf[IdentNode]
  }

  override def visitArrayElemLHS(ctx: WaccParser.ArrayElemLHSContext):
  AssignmentLeftNode = {
    //    println("hit " + currentMethodName())

    visit(ctx.getChild(0)).asInstanceOf[ArrayElemNode]
  }

  override def visitPairElemLHS(ctx: WaccParser.PairElemLHSContext):
  AssignmentLeftNode = {
    //    println("hit " + currentMethodName())

    visit(ctx.getChild(0)).asInstanceOf[PairElemNode]
  }

  override def visitExprRHS(ctx: ExprRHSContext): AssignmentRightNode = {
    //    println("hit " + currentMethodName())

    visit(ctx.getChild(0)).asInstanceOf[ExprNode]
  }

  override def visitArrayLiteralRHS(ctx: ArrayLiteralRHSContext):
  AssignmentRightNode = {
    //    println("hit " + currentMethodName())

    visit(ctx.getChild(0)).asInstanceOf[ArrayLiteralNode]
  }

  override def visitNewPairRHS(ctx: NewPairRHSContext): AssignmentRightNode = {
    //    println("hit " + currentMethodName())

    val fstElem: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]
    val sndElem: ExprNode = visit(ctx.getChild(4)).asInstanceOf[ExprNode]

    NewPairNode(fstElem, sndElem)
  }

  override def visitPairElemRHS(ctx: PairElemRHSContext): AssignmentRightNode
  = {
    //    println("hit " + currentMethodName())

    visit(ctx.getChild(0)).asInstanceOf[PairElemNode]
  }

  override def visitCallRHS(ctx: CallRHSContext): AssignmentRightNode = {
    //    println("hit " + currentMethodName())

    val noOfChildren = ctx.getChildCount
    val hasArgList = noOfChildren == 5

    val id: IdentNode = visit(ctx.getChild(1)).asInstanceOf[IdentNode]
    val argList: Option[ArgListNode] =
      if (hasArgList) {
        Some(visit(ctx.getChild(3)).asInstanceOf[ArgListNode])
      } else None

    CallNode(id, argList)
  }

  override def visitFstElem(ctx: FstElemContext): PairElemNode = {
    //    println("hit " + currentMethodName())

    val exprChild: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    FstNode(exprChild)
  }

  override def visitSndElem(ctx: SndElemContext): PairElemNode = {
    //    println("hit " + currentMethodName())

    val exprChild: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    SndNode(exprChild)
  }

  override def visitBaseType(ctx: WaccParser.BaseTypeContext): TypeNode = {
    //    println("hit " + currentMethodName())

    visit(ctx.getChild(0)).asInstanceOf[BaseTypeNode]
  }

  override def visitArrayType(ctx: ArrayTypeContext): TypeNode = {
    //    println("hit " + currentMethodName())

    val elemType: TypeNode = visit(ctx.getChild(0)).asInstanceOf[TypeNode]
    ArrayTypeNode(elemType)
  }

  override def visitPairType(ctx: WaccParser.PairTypeContext): TypeNode = {
    //    println("hit " + currentMethodName())

    visit(ctx.getChild(0)).asInstanceOf[PairTypeNode]
  }

  override def visitIntType(ctx: IntTypeContext): BaseTypeNode = {
    //    println("hit " + currentMethodName())

    IntTypeNode()
  }

  override def visitBoolType(ctx: BoolTypeContext): BaseTypeNode = {
    //    println("hit " + currentMethodName())

    BoolTypeNode()
  }

  override def visitCharType(ctx: CharTypeContext): BaseTypeNode = {
    //    println("hit " + currentMethodName())

    CharTypeNode()
  }

  override def visitStringType(ctx: StringTypeContext): BaseTypeNode = {
    //    println("hit " + currentMethodName())

    StringTypeNode()
  }

  override def visitPair_type(ctx: WaccParser.Pair_typeContext): PairTypeNode
  = {
    //    println("hit " + currentMethodName())

    val firstElemType: PairElemTypeNode = visit(ctx.getChild(2))
      .asInstanceOf[PairElemTypeNode]
    val secondElemType: PairElemTypeNode = visit(ctx.getChild(4))
      .asInstanceOf[PairElemTypeNode]

    PairTypeNode(firstElemType, secondElemType)
  }

  override def visitBaseTypePairElem(ctx: BaseTypePairElemContext):
  PairElemTypeNode = {
    //    println("hit " + currentMethodName())

    visit(ctx.getChild(0)).asInstanceOf[BaseTypeNode]
  }

  override def visitArrayTypePairElem(ctx: ArrayTypePairElemContext):
  PairElemTypeNode = {
    //    println("hit " + currentMethodName())
    val elemType: TypeNode = visit(ctx.getChild(0)).asInstanceOf[TypeNode]
    ArrayTypeNode(elemType)
  }

  override def visitPairTypePairElem(ctx: PairTypePairElemContext):
  PairElemTypeNode = {
    //    println("hit " + currentMethodName())
    InnerPairTypeNode()
  }

  override def visitIntLiteral(ctx: IntLiteralContext): ExprNode = {
    val noOfChildren = ctx.getChildCount
    if (noOfChildren == 1) {
      visit(ctx.getChild(0)).asInstanceOf[IntLiteralNode]
    } else {
      val sign = ctx.getChild(0).getText
      val posIntNode = visit(ctx.getChild(1)).asInstanceOf[IntLiteralNode]
      sign match {
        case "+" => posIntNode
        case "-" => negateIntLiteralNode(posIntNode)
      }
    }

  }

  override def visitInt_liter(ctx: WaccParser.Int_literContext):
  IntLiteralNode = {
    try {
      val value = Integer.parseInt(ctx.getText)
      IntLiteralNode(value)
    } catch {
      case _: NumberFormatException =>
        SyntaxErrorLog.add("Integer is too large/small to store in 32 bits.")
        IntLiteralNode(-1)
    }

  }

  def negateIntLiteralNode(intNode: IntLiteralNode): IntLiteralNode = {
    IntLiteralNode(- intNode.value)
  }

  override def visitPairLiteral(ctx: WaccParser.PairLiteralContext): ExprNode
  = {
    //    println("hit " + currentMethodName())
    visit(ctx.getChild(0)).asInstanceOf[PairLiteralNode]
  }

  override def visitPair_liter(ctx: WaccParser.Pair_literContext):
  PairLiteralNode = {
    //    println("hit " + currentMethodName())
    PairLiteralNode()
  }

  override def visitBoolLiteral(ctx: WaccParser.BoolLiteralContext): ExprNode
  = {
    //    println("hit " + currentMethodName())
    visit(ctx.getChild(0)).asInstanceOf[BoolLiteralNode]
  }

  override def visitBool_liter(ctx: WaccParser.Bool_literContext):
  BoolLiteralNode = {
    //    println("hit " + currentMethodName())
    val value = ctx.getText.toBoolean

    BoolLiteralNode(value)
  }

  override def visitCharLiteral(ctx: WaccParser.CharLiteralContext): ExprNode
  = {
    //    println("hit " + currentMethodName())
    visit(ctx.getChild(0)).asInstanceOf[CharLiteralNode]
  }

  override def visitChar_liter(ctx: WaccParser.Char_literContext):
  CharLiteralNode = {
    //    println("hit " + currentMethodName())
    val value = sanitise(ctx.getText).charAt(1)

    CharLiteralNode(value)
  }

  // Input: String that contains literal escape characters "\\\\hello \\n"
  // Output: String that contains actual escape characters "\\hello \n"
  private def sanitise(input: String): String = {
    var output = input
    val escapeChars = IndexedSeq("0", "b", "t", "n", "f", "r", "\"", "'", "\\")
//    output = "\\0".r.replaceAllIn(output, "\0")
    output = "\\b".r.replaceAllIn(output, "\b")
    output = "\\t".r.replaceAllIn(output, "\t")
    output = "\\n".r.replaceAllIn(output, "\n")
    output = "\\f".r.replaceAllIn(output, "\f")
    output = "\\r".r.replaceAllIn(output, "\r")
    output = "\\\"".r.replaceAllIn(output, "\"")
    output = "\\'".r.replaceAllIn(output, "'")
    output = "\\\\".r.replaceAllIn(output, "\\")

    output
  }

  override def visitStringLiteral(ctx: WaccParser.StringLiteralContext):
  ExprNode = {
    //    println("hit " + currentMethodName())
    visit(ctx.getChild(0)).asInstanceOf[StringLiteralNode]
  }

  override def visitStr_liter(ctx: WaccParser.Str_literContext):
  StringLiteralNode = {
    //    println("hit " + currentMethodName())
    val value = sanitise(ctx.getText).tail.init

    StringLiteralNode(value)
  }

  override def visitUnaryOperation(ctx: UnaryOperationContext): ExprNode = {
    //    println("hit " + currentMethodName())
    val argument: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(0).getText

    operation match {
      case "!" => LogicalNotNode(argument)
      case "-" => NegationNode(argument)
      case "len" => LenNode(argument)
      case "ord" => OrdNode(argument)
      case "chr" => ChrNode(argument)
      case e: Any => throw new RuntimeException("Unknown Unary Operand.")
    }
  }

  override def visitBinaryOperation1(ctx: BinaryOperation1Context): ExprNode = {
    //    println("hit " + currentMethodName())
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case "*" => MulNode(leftExpr, rightExpr)
      case "/" => DivNode(leftExpr, rightExpr)
      case "%" => ModNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with " +
        "precedence 1.")
    }
  }

  override def visitBinaryOperation2(ctx: BinaryOperation2Context): ExprNode = {
    //    println("hit " + currentMethodName())
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case "+" => PlusNode(leftExpr, rightExpr)
      case "-" => MinusNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with " +
        "precedence 2.")
    }
  }

  override def visitBinaryOperation3(ctx: BinaryOperation3Context): ExprNode = {

    //    println("hit " + currentMethodName())
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case ">" => GreaterThanNode(leftExpr, rightExpr)
      case ">=" => GreaterEqualNode(leftExpr, rightExpr)
      case "<" => LessThanNode(leftExpr, rightExpr)
      case "<=" => LessEqualNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with " +
        "precedence 3.")
    }
  }

  override def visitBinaryOperation4(ctx: BinaryOperation4Context): ExprNode = {
    //    println("hit " + currentMethodName())
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case "==" => DoubleEqualNode(leftExpr, rightExpr)
      case "!=" => NotEqualNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with " +
        "precedence 4.")
    }
  }

  override def visitBinaryOperation5(ctx: BinaryOperation5Context): ExprNode = {
    //    println("hit " + currentMethodName())
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case "&&" => LogicalAndNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with " +
        "precedence 5.")
    }
  }

  override def visitBinaryOperation6(ctx: BinaryOperation6Context): ExprNode = {
    //    println("hit " + currentMethodName())
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case "||" => LogicalOrNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with " +
        "precedence 6.")
    }
  }

  override def visitParens(ctx: WaccParser.ParensContext): ExprNode = {
    //    println("hit " + currentMethodName())
    visit(ctx.getChild(1)).asInstanceOf[ExprNode]
  }

  override def visitArrayElem(ctx: WaccParser.ArrayElemContext): AstNode = {
    //    println("hit " + currentMethodName())
    val noOfChildren = ctx.getChildCount

    val identifier: IdentNode = visit(ctx.getChild(0)).asInstanceOf[IdentNode]
    val indices: IndexedSeq[ExprNode] =
      for (i <- 0 until noOfChildren if i % 3 == 2)
        yield visit(ctx.getChild(i)).asInstanceOf[ExprNode]

    ArrayElemNode(identifier, indices)
  }

  override def visitIdentifier(ctx: WaccParser.IdentifierContext): IdentNode = {
    //    println("hit " + currentMethodName())
    val name = ctx.getText

    IdentNode(name)
  }

  override def visitArrayElemExpr(ctx: ArrayElemExprContext): ExprNode = {
    //    println("hit " + currentMethodName())
    visit(ctx.getChild(0)).asInstanceOf[ArrayElemNode]
  }

  override def visitArray_liter(ctx: WaccParser.Array_literContext):
  ArrayLiteralNode = {
    //    println("hit " + currentMethodName())
    val noOfChildren = ctx.getChildCount
    val values: IndexedSeq[ExprNode] =
      for (i <- 0 until noOfChildren if i % 2 == 1)
        yield visit(ctx.getChild(i)).asInstanceOf[ExprNode]

    ArrayLiteralNode(if (noOfChildren == 2) IndexedSeq[ExprNode]() else
      values)
  }

  override def visitIdentExpr(ctx: IdentExprContext): ExprNode = {
    //    println("hit " + currentMethodName())
    visit(ctx.getChild(0)).asInstanceOf[IdentNode]
  }

  override def visitArg_list(ctx: Arg_listContext): ArgListNode = {
    val noOfChildren = ctx.getChildCount
    val exprChildren: IndexedSeq[ExprNode] =
      for (i <- 0 until noOfChildren if i % 2 == 0)
        yield visit(ctx.getChild(i)).asInstanceOf[ExprNode]

    ArgListNode(exprChildren)
  }

}
