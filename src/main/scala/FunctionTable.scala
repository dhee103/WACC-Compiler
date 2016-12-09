import collection.mutable.HashMap

// FunctionTable is an object as there is a single namespace for functions in
// WACC programs.
// Maps function names (String) to function nodes (FuncNodes)
object FunctionTable {
  val dict = new HashMap[String, (FuncNode, Boolean)]()

  def add(func: FuncNode): Unit = {
    val identName: String = func.identifier.name

    if (dict.contains(identName)) {
      SemanticErrorLog.add(s"Attempted to redefine function $identName.")
    } else {
      dict += (identName -> (func, false))
    }
  }

  def getReturnType(ident: IdentNode): TypeNode = getFuncNode(ident).returnType

  private def getParamList(ident: IdentNode): List[ParamNode] = {
    getFuncNode(ident).paramList.params.toList
  }

  def getParamTypes(ident: IdentNode): List[TypeNode] = {
    val paramList: List[ParamNode] = getParamList(ident)
    for (param <- paramList) yield param.variableType
  }

  def getParamIdents(ident: IdentNode): List[IdentNode] = {
    val paramList: List[ParamNode] = getParamList(ident)
    for (param <- paramList) yield IdentNode(param.identifier.name, Some(param.variableType))
  }

  def getNoOfParams(ident: IdentNode): Int = getParamList(ident).size

  def getLocalVars(ident: IdentNode): List[IdentNode] = getFuncNode(ident).localVars

  def getNoOfLocalVars(ident: IdentNode): Int = getLocalVars(ident).size

  def getBody(ident: IdentNode): StatNode = getFuncNode(ident).statement

  private def lookup(ident: IdentNode): (FuncNode, Boolean) = {
    dict.getOrElse(ident.name, throw new RuntimeException(s"Fatal Error: Function ${ident.name} not found in Function Table.")) // TODO: Change this?
  }

  private def getFuncNode(ident: IdentNode): FuncNode = {
    lookup(ident)._1
  }

  def hasBeenGenerated(ident: IdentNode): Boolean = {
    lookup(ident)._2
  }

  def markAsGenerated(ident: IdentNode): Unit = {
    val identName = ident.name
    val func = getFuncNode(ident)
    dict += (identName -> (func, true))
  }

  def doesContain(ident: IdentNode): Boolean = {
    try {
      lookup(ident)
      true
    } catch {
      case _: Throwable => false
    }
  }
}
