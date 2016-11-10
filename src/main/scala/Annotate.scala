import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => univ}

class Annotate(val _AST: ProgNode) {

  private var AST = _AST

  private var STable: SymbolTable = new SymbolTable(null)

  //def getAnnotatedAST

  def annotateStatNode(statement: StatNode, currentScopeSymbolTable: SymbolTable): Unit = {

    var statementType: String = getType(statement)
    statementType match {

      case "DeclarationNode" => annotateDeclarationNode(statement.asInstanceOf[DeclarationNode], STable)
      case "AssignmentNode"  => annotateAssignmentNode(statement.asInstanceOf[AssignmentNode], STable)
      case "ReadNode"        => annotateReadNode(statement.asInstanceOf[ReadNode], STable)
      case "FreeNode"        => annotateFreeNode(statement.asInstanceOf[FreeNode], STable)
      case "ReturnNode"      => annotateReturnNode(statement.asInstanceOf[ReturnNode], STable)
      case "ExitNode"        => annotateExitNode(statement.asInstanceOf[ExitNode], STable)
      case "PrintNode"       => annotatePrintNode(statement.asInstanceOf[PrintNode], STable)
      case "PrintlnNode"     => annotatePrintlnNode(statement.asInstanceOf[PrintlnNode], STable)
      case "IfNode"          => annotateIfNode(statement.asInstanceOf[IfNode], new SymbolTable(STable))
      case "WhileNode"       => annotateWhileNode(statement.asInstanceOf[WhileNode], new SymbolTable(STable))
      case "NewBeginNode"    => annotateNewBeginNode(statement.asInstanceOf[NewBeginNode], new SymbolTable(STable))
      case "SequenceNode"    => annotateSequenceNode(statement.asInstanceOf[SequenceNode], STable)


    }
  }

  def AnnotateExprNode(expression: ExprNode, currentScopeSymbolTable: SymbolTable): Unit = {

    var exprType: String = getType(expression)

    exprType match {

      case "IdentNode" => annotateIdentNode(expression.asInstanceOf[IdentNode], currentScopeSymbolTable)
      case "LogicalNotNode" => annotateUnaryOperationNode(expression.asInstanceOf[LogicalNotNode], currentScopeSymbolTable)
      case "OrdNode" => annotateUnaryOperationNode(expression.asInstanceOf[OrdNode], currentScopeSymbolTable)
      case "ChrNode" => annotateUnaryOperationNode(expression.asInstanceOf[ChrNode], currentScopeSymbolTable)
      case "LenNode"  => annotateUnaryOperationNode(expression.asInstanceOf[LenNode], currentScopeSymbolTable)
      case "NegativeNode" => annotateUnaryOperationNode(expression.asInstanceOf[NegativeNode], currentScopeSymbolTable)

    }

    def annotateIdentNode(identifier: IdentNode, currentST: SymbolTable): Unit = {

      identifier.nodeType = currentST.lookupAll(identifier)
      //may throw exceptions here

    }

    def annotateUnaryOperationNode(unOPNode: UnaryOperationNode, currentST: SymbolTable) = {

    }


  }

    def annotateDeclarationNode(statement: DeclarationNode, currentST: SymbolTable): Unit = {

     var ident: IdentNode = statement.identifier

     ident.nodeType = statement.variableType

     currentST.add(ident, ident.nodeType)

     statement.identifier = ident

    }

    def annotateAssignmentNode(statement: AssignmentNode, currentST: SymbolTable): Unit = {

      var lhsNode: IdentNode = null

      var rhsNode: IdentNode = null

      if(getType(statement.lhs) == "IdentNode"){

        lhsNode = lhsNode.asInstanceOf[IdentNode]

        lhsNode.nodeType = currentST.lookupAll(lhsNode)

      }

      if(getType(statement.rhs) == "IdentNode"){

        rhsNode = rhsNode.asInstanceOf[IdentNode]

        rhsNode.nodeType = currentST.lookupAll(rhsNode)

      }

      statement.rhs = rhsNode

      statement.lhs = lhsNode

    }

    def annotateReadNode(statement: ReadNode, currentST: SymbolTable): Unit = {

      var readTarget: IdentNode =  null

      if(getType(statement.variable) == "IdentNode"){

        readTarget = statement.variable.asInstanceOf[IdentNode]

        readTarget.nodeType = currentST.lookupAll(readTarget)



      }

    }

    def annotateFreeNode(statement: FreeNode, currentST: SymbolTable): Unit = {

    }

    def annotateReturnNode(statement: ReturnNode, currentST: SymbolTable): Unit = {

    }

    def annotateExitNode(statement: ExitNode, currentST: SymbolTable): Unit = {

    }

    def annotatePrintNode(statement: PrintNode, currentST: SymbolTable): Unit = {

    }

    def annotatePrintlnNode(statement: PrintlnNode, currentST: SymbolTable): Unit = {

    }

    def annotateIfNode(statement: IfNode, currentST: SymbolTable): Unit = {

    }

    def annotateWhileNode(statement: WhileNode, currentST: SymbolTable): Unit = {

    }

    def annotateNewBeginNode(statement: NewBeginNode, currentST: SymbolTable): Unit = {

    }

    def annotateSequenceNode(statement: SequenceNode, currentST: SymbolTable): Unit = {

    }





  def getType[T](obj: T): String = {
    val str: String = obj.getClass().toString
    str.substring(6, str.length)
  }

  def checkNodesHaveSameType(typeNode1: TypeNode, typeNode2: TypeNode): Boolean = {
    def f[A, B: ClassTag](a: A, b: B) = a match {
      case _: B => true
      case _    => false
    }
    f(typeNode1, typeNode2)
  }



}
