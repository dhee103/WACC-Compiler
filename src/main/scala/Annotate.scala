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

    def annotateDeclarationNode(statement: DeclarationNode, currentST: SymbolTable): Unit = {

     val ident: IdentNode = statement.identifier

     ident.nodeType = statement.variableType

     currentST.add(ident, ident.nodeType)

    }

    def annotateAssignmentNode(statement: AssignmentNode, currentST: SymbolTable): Unit = {

      val lhsNode = statement.lhs

      if(getType(lhsNode) == "IdentNode"){



      }







    }

    def annotateReadNode(statement: ReadNode, currentST: SymbolTable): Unit = {

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
