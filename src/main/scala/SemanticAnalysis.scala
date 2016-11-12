object SemanticAnalysis {

  var numErrors: Int = 0

  def typeCheck(ast: ProgNode): Unit = {
    // TO DO: functions

    typeCheckStat(ast.statChild)
  }

  def typeCheckStat(stat: StatNode): Unit = {

    val statementType: String = Annotate.getType(stat)

    statementType match {

      case "DeclarationNode" => typeCheckDeclaration(stat.asInstanceOf[DeclarationNode])
      case "AssignmentNode" =>
      case "ReadNode" =>
      case "FreeNode" =>
      case "ReturnNode" =>
      case "ExitNode" =>
      case "PrintNode" =>
      case "PrintlnNode" =>
      case "IfNode" =>
      case "WhileNode" =>
      case "NewBeginNode" =>
      case "SequenceNode" =>

    }
  }

  def typeCheckDeclaration(decl: DeclarationNode): Unit = {
    val identType = decl.identifier.typeVal


  }


}
