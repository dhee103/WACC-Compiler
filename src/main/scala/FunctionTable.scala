import collection.mutable.HashMap

// FunctionTable is an object as there is a single namespace for functions in
// WACC programs.
// Maps function identifiers to return types,
// list of params (pair of type and ident), list of locals vars.
object FunctionTable {
// TODO: consider having tuple of TypeNode and paramList
  val dict = new HashMap[IdentNode, (TypeNode, List[ParamNode], List[IdentNode])]()

  def add(func: FuncNode): Unit = {
    val identifier: IdentNode = func.identifier
    val returnType: TypeNode = func.returnType
    val paramList = func.paramList.params.toList
    val localVars: List[IdentNode] = func.localVars


    if (dict.contains(identifier)) {
      val name = identifier.name
      SemanticErrorLog.add(s"Attempted to redefine function $name.")
    } else {
      dict += (identifier -> (returnType, paramList, localVars))
    }
  }

  def getReturnType(ident: IdentNode): TypeNode = lookup(ident)._1

  def getParamTypes(ident: IdentNode): List[TypeNode] = {
    val paramList: List[ParamNode] = lookup(ident)._2
    for (param <- paramList) yield param.variableType
  }

  def getParamIdents(ident: IdentNode): List[IdentNode] = {
    val paramList: List[ParamNode] = lookup(ident)._2
    for (param <- paramList) yield param.identifier
  }

  def getLocalVars(ident: IdentNode): List[IdentNode] = lookup(ident)._3

  def getNoOfLocalVars(ident: IdentNode): Int = getLocalVars(ident).size

  private def lookup(ident: IdentNode): (TypeNode, List[ParamNode], List[IdentNode]) = {
    dict.getOrElse(ident, throw new RuntimeException("Variable used but not in scope")) // TODO: Change this?
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
