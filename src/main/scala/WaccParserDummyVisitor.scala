import WaccParser._
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

class WaccParserDummyVisitor extends WaccParserBaseVisitor[AstNode] {

  override def visitProg(ctx: WaccParser.ProgContext): AstNode = {
    val noOfChildren = ctx.getChildCount
    val statChild: StatNode = visit(ctx.getChild(noOfChildren - 2)).asInstanceOf[StatNode]

    val funcChildren: IndexedSeq[FuncNode] =
      for (i <- 1 until noOfChildren - 3) yield visit(ctx.getChild(i)).asInstanceOf[FuncNode]
    new ProgNode(statChild, funcChildren)
  }

  override def visitFunc(ctx: WaccParser.FuncContext): AstNode = super.visitFunc(ctx)

  override def visitParam_list(ctx: WaccParser.Param_listContext): AstNode = super.visitParam_list(ctx)

  override def visitParam(ctx: WaccParser.ParamContext): AstNode = super.visitParam(ctx)

  override def visitSkipStat(ctx: WaccParser.SkipStatContext): SkipStatNode = {
    new SkipStatNode()
  }

  override def visitDeclaration(ctx: WaccParser.DeclarationContext): DeclarationNode = {
    val variableType: TypeNode = visit(ctx.getChild(0)).asInstanceOf[TypeNode]
    val identifier: IdentNode = visit(ctx.getChild(1)).asInstanceOf[IdentNode]
    val rhs: AssignmentRightNode =  visit(ctx.getChild(3)).asInstanceOf[AssignmentRightNode]

    new DeclarationNode(variableType, identifier, rhs)
  }

  override def visitAssignment(ctx: WaccParser.AssignmentContext): AssignmentNode = {
    val lhs: AssignmentLeftNode = visit(ctx.getChild(0)).asInstanceOf[AssignmentLeftNode]
    val rhs: AssignmentRightNode = visit(ctx.getChild(2)).asInstanceOf[AssignmentRightNode]

    new AssignmentNode(lhs, rhs)
  }

  override def visitRead(ctx: WaccParser.ReadContext): ReadNode = {
    val variable: AssignmentLeftNode = visit(ctx.getChild(1)).asInstanceOf[AssignmentLeftNode]

    new ReadNode(variable)
  }

  override def visitFree(ctx: WaccParser.FreeContext): FreeNode = {
    val variable: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    new FreeNode(variable)
  }

  override def visitReturn(ctx: WaccParser.ReturnContext): ReturnNode = {
    val returnValue: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    new ReturnNode(returnValue)
  }

  override def visitExit(ctx: WaccParser.ExitContext): ExitNode = {
    val exitCode: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    new ExitNode(exitCode)
  }

  override def visitPrint(ctx: WaccParser.PrintContext): PrintNode = {
    val text: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    new PrintNode(text)
  }

  override def visitPrintln(ctx: WaccParser.PrintlnContext): PrintlnNode = {
    val text: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    new PrintlnNode(text)
  }

  override def visitIf(ctx: WaccParser.IfContext): IfNode = {
    val condition: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]
    val thenStat: StatNode = visit(ctx.getChild(3)).asInstanceOf[StatNode]
    val elseStat: StatNode = visit(ctx.getChild(5)).asInstanceOf[StatNode]

    new IfNode(condition, thenStat, elseStat)
  }

  override def visitWhile(ctx: WaccParser.WhileContext): WhileNode = {
    val condition: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]
    val loopBody: StatNode = visit(ctx.getChild(3)).asInstanceOf[StatNode]

    new WhileNode(condition, loopBody)
  }

  override def visitNewBegin(ctx: WaccParser.NewBeginContext): NewBeginNode = {
    val body: StatNode = visit(ctx.getChild(1)).asInstanceOf[StatNode]

    new NewBeginNode(body)
  }

  override def visitSequence(ctx: WaccParser.SequenceContext): SequenceNode = {
    val fstStat: StatNode = visit(ctx.getChild(0)).asInstanceOf[StatNode]
    val sndStat: StatNode = visit(ctx.getChild(2)).asInstanceOf[StatNode]

    new SequenceNode(fstStat, sndStat)
  }

  override def visitIdentLHS(ctx: WaccParser.IdentLHSContext): AssignmentLeftNode = {
    visit(ctx.getChild(0)).asInstanceOf[IdentNode]
  }

  override def visitArrayElemLHS(ctx: WaccParser.ArrayElemLHSContext): AssignmentLeftNode = {
    visit(ctx.getChild(0)).asInstanceOf[ArrayElemNode]
  }

  override def visitPairElemLHS(ctx: WaccParser.PairElemLHSContext): AssignmentLeftNode = {
    visit(ctx.getChild(0)).asInstanceOf[PairElemNode]
  }

  override def visitExprRHS(ctx: ExprRHSContext): AssignmentRightNode = {
    visit(ctx.getChild(0)).asInstanceOf[ExprNode]
  }

  override def visitArrayLiteralRHS(ctx: ArrayLiteralRHSContext): AssignmentRightNode = {
    visit(ctx.getChild(0)).asInstanceOf[ArrayLiteralNode]
  }

  override def visitNewPairRHS(ctx: NewPairRHSContext): AssignmentRightNode = {
    val fstElem: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]
    val sndElem: ExprNode = visit(ctx.getChild(4)).asInstanceOf[ExprNode]

    new NewPairNode(fstElem, sndElem)
  }

  override def visitPairElemRHS(ctx: PairElemRHSContext): AssignmentRightNode = {
    visit(ctx.getChild(0)).asInstanceOf[PairElemNode]
  }

  override def visitCallRHS(ctx: CallRHSContext): AssignmentRightNode = {
    val noOfChildren = ctx.getChildCount
    val hasArgList = noOfChildren == 5

    val id: IdentNode = visit(ctx.getChild(1)).asInstanceOf[IdentNode]
    val argList: Option[ArgListNode] =
      if (hasArgList) {
        Some(visit(ctx.getChild(3)).asInstanceOf[ArgListNode])
      } else None

    new CallNode(id, argList)
  }

  override def visitFstElem(ctx: FstElemContext): PairElemNode = {
    val exprChild: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    new FstNode(exprChild)
  }

  override def visitSndElem(ctx: SndElemContext): PairElemNode = {
    val exprChild: ExprNode = visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    new SndNode(exprChild)
  }

  override def visitBaseType(ctx: WaccParser.BaseTypeContext): TypeNode = {
    visit(ctx.getChild(0)).asInstanceOf[BaseTypeNode]
  }

  override def visitArrayType(ctx: ArrayTypeContext): TypeNode = {
    val elemType: TypeNode = visit(ctx.getChild(0)).asInstanceOf[TypeNode]
    new ArrayTypeNode(elemType)
  }

  override def visitPairType(ctx: WaccParser.PairTypeContext): TypeNode = {
    visit(ctx.getChild(0)).asInstanceOf[PairTypeNode]
  }

  override def visitIntType(ctx: IntTypeContext): BaseTypeNode = {
    new IntTypeNode
  }

  override def visitBoolType(ctx: BoolTypeContext): BaseTypeNode = {
    new BoolTypeNode
  }

  override def visitCharType(ctx: CharTypeContext): BaseTypeNode = {
    new CharTypeNode
  }

  override def visitStringType(ctx: StringTypeContext): BaseTypeNode = {
    new StringTypeNode
  }

  override def visitPair_type(ctx: WaccParser.Pair_typeContext): PairTypeNode = {
    val firstElemType: PairElemTypeNode = visit(ctx.getChild(1)).asInstanceOf[PairElemTypeNode]
    val secondElemType: PairElemTypeNode = visit(ctx.getChild(3)).asInstanceOf[PairElemTypeNode]

    new PairTypeNode(firstElemType, secondElemType)
  }

  override def visitBaseTypePairElem(ctx: BaseTypePairElemContext): PairElemTypeNode = {
    visit(ctx.getChild(0)).asInstanceOf[BaseTypeNode]
  }

  override def visitArrayTypePairElem(ctx: ArrayTypePairElemContext): PairElemTypeNode = {
    val elemType: TypeNode = visit(ctx.getChild(0)).asInstanceOf[TypeNode]
    new ArrayTypeNode(elemType)
  }

  override def visitPairTypePairElem(ctx: PairTypePairElemContext): PairElemTypeNode = {
    new InnerPairTypeNode()
  }

  override def visitIntLiteral(ctx: IntLiteralContext): ExprNode = {
    visit(ctx.getChild(0)).asInstanceOf[IntLiteralNode]
  }

  override def visitInt_liter(ctx: WaccParser.Int_literContext): IntLiteralNode = {
    val value = Integer.parseInt(ctx.getText)

    new IntLiteralNode(value)
  }

  override def visitPairLiteral(ctx: WaccParser.PairLiteralContext): ExprNode = {
    visit(ctx.getChild(0)).asInstanceOf[PairLiteralNode]
  }

  override def visitPair_liter(ctx: WaccParser.Pair_literContext): PairLiteralNode = {
    new PairLiteralNode()
  }

  override def visitBoolLiteral(ctx: WaccParser.BoolLiteralContext): ExprNode = {
    visit(ctx.getChild(0)).asInstanceOf[BoolLiteralNode]
  }

  override def visitBool_liter(ctx: WaccParser.Bool_literContext): BoolLiteralNode = {
    val value = ctx.getText.toBoolean

    new BoolLiteralNode(value)
  }

  override def visitCharLiteral(ctx: WaccParser.CharLiteralContext): ExprNode = {
    visit(ctx.getChild(0)).asInstanceOf[CharLiteralNode]
  }

  override def visitChar_liter(ctx: WaccParser.Char_literContext): CharLiteralNode = {
    val value = ctx.getText.charAt(0)

    new CharLiteralNode(value)
  }

  override def visitStringLiteral(ctx: WaccParser.StringLiteralContext): ExprNode = {
    visit(ctx.getChild(0)).asInstanceOf[StringLiteralNode]
  }

  override def visitStr_liter(ctx: WaccParser.Str_literContext): StringLiteralNode = {
    val value = ctx.getText

    new StringLiteralNode(value)
  }

  override def visitUnaryOperation(ctx: UnaryOperationContext): ExprNode = {
    val argument: ExprNode =visit(ctx.getChild(1)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case "!" => new LogicalNotNode(argument)
      case "-" => new NegativeNode(argument)
      case "len" => new LenNode(argument)
      case "ord" => new OrdNode(argument)
      case "chr" => new ChrNode(argument)
      case _ => throw new RuntimeException("Unknown Unary Operand.")
    }
  }

  override def visitBinaryOperation1(ctx: BinaryOperation1Context): ExprNode = {
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case "*" => new MulNode(leftExpr, rightExpr)
      case "/" => new DivNode(leftExpr, rightExpr)
      case "%" => new ModNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with precedence 1.")
    }
  }

  override def visitBinaryOperation2(ctx: BinaryOperation2Context): ExprNode = {
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case "+" => new PlusNode(leftExpr, rightExpr)
      case "-" => new MinusNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with precedence 2.")
    }
  }

  override def visitBinaryOperation3(ctx: BinaryOperation3Context): ExprNode = {
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case ">" => new GreaterThanNode(leftExpr, rightExpr)
      case ">=" => new GreaterEqualNode(leftExpr, rightExpr)
      case "<" => new LessThanNode(leftExpr, rightExpr)
      case "<=" => new LessEqualNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with precedence 3.")
    }
  }

  override def visitBinaryOperation4(ctx: BinaryOperation4Context): ExprNode = {
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case "==" => new DoubleEqualNode(leftExpr, rightExpr)
      case "!=" => new NotEqualNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with precedence 4.")
    }
  }

  override def visitBinaryOperation5(ctx: BinaryOperation5Context): ExprNode = {
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case "&&" => new LogicalAndNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with precedence 5.")
    }
  }

  override def visitBinaryOperation6(ctx: BinaryOperation6Context): ExprNode = {
    val leftExpr: ExprNode = visit(ctx.getChild(0)).asInstanceOf[ExprNode]
    val rightExpr: ExprNode = visit(ctx.getChild(2)).asInstanceOf[ExprNode]

    val operation = ctx.getChild(1).getText

    operation match {
      case "||" => new LogicalAndNode(leftExpr, rightExpr)
      case _ => throw new RuntimeException("Unknown Binary Operand with precedence 6.")
    }
  }

  override def visitParens(ctx: WaccParser.ParensContext): ExprNode = {
    visit(ctx.getChild(1)).asInstanceOf[ExprNode]
  }

  override def visitArrayElem(ctx: WaccParser.ArrayElemContext): AstNode = super.visitArrayElem(ctx)

  override def visitUnary_oper(ctx: WaccParser.Unary_operContext): AstNode = super.visitUnary_oper(ctx)

  override def visitIdentifier(ctx: WaccParser.IdentifierContext): IdentNode = {
    val name = ctx.getText

    new IdentNode(name)
  }

  override def visitArrayElemExpr(ctx: ArrayElemExprContext): ExprNode  = {
    visit(ctx.getChild(0)).asInstanceOf[ArrayElemNode]
  }

  override def visitArray_liter(ctx: WaccParser.Array_literContext): AstNode = super.visitArray_liter(ctx)

  override def visitIdentExpr(ctx: IdentExprContext): ExprNode = {
    visit(ctx.getChild(0)).asInstanceOf[IdentNode]
  }

  override def visitArg_list(ctx: Arg_listContext): AstNode = super.visitArg_list(ctx)

}
