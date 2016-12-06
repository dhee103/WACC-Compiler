import collection.mutable.HashMap

// FunctionTable is an object as there is a single namespace for functions in
// WACC programs.
// Maps function identifiers to return types, list of param types,
// list of locals vars, no. of local vars
object FunctionTable {
// TODO: consider having tuple of TypeNode and paramList
// TODO: consider having an trait/abstract class for functionTable & SymbolTable
  val dict = new HashMap[IdentNode, (TypeNode, List[TypeNode], List[IdentNode], Int)]()

  def add(func: FuncNode): Unit = {
    val identifier: IdentNode = func.identifier
    val returnType: TypeNode = func.returnType
    val paramList = func.paramList
    val paramTypes: List[TypeNode] =
      (for (param <- paramList.params) yield param.variableType).toList
    val localVars: List[IdentNode] = func.localVars
    val noOfLocalVars: Int = func.noOfLocalVars


    if (dict.contains(identifier)) {
      val name = identifier.name
      SemanticErrorLog.add(s"Attempted to redefine function $name.")
    } else {
      dict += (identifier -> (returnType, paramTypes, localVars, noOfLocalVars))
    }
  }

  def getReturnType(ident: IdentNode): TypeNode = lookup(ident)._1

  def getParamTypes(ident: IdentNode): List[TypeNode] = lookup(ident)._2

  def getLocalVars(ident: IdentNode): List[IdentNode] = lookup(ident)._3

  def getNoOfLocalVars(ident: IdentNode): Int = getLocalVars(ident).size

  private def lookup(ident: IdentNode): (TypeNode, List[TypeNode], List[IdentNode], Int) = {
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
