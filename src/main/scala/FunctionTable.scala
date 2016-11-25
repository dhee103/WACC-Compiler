import collection.mutable.HashMap

object FunctionTable {
// TODO: consider having tuple of TypeNode and paramList
// TODO: consider having an trait/abstract class for functionTable & SymbolTable
  val dict = new HashMap[IdentNode, (TypeNode, IndexedSeq[TypeNode], Int)]()

  def add(func: FuncNode): Unit = {
    val identifier: IdentNode = func.identifier
    val returnType: TypeNode = func.returnType
    val paramList = func.paramList
    val paramTypes: IndexedSeq[TypeNode] =
      for (param <- paramList.params) yield param.variableType
    val noOfLocalVars: Int = func.noOfLocalVars


    if (dict.contains(identifier)) {
      val name = identifier.name
      SemanticErrorLog.add(s"Attempted to redefine function $name.")
    } else {
      dict += (identifier -> (returnType, paramTypes, noOfLocalVars))
    }
  }

  def getReturnType(ident: IdentNode) = lookup(ident)._1

  def getParamTypes(ident: IdentNode): IndexedSeq[TypeNode] = lookup(ident)._2

  def getNoOfLocalVars(ident: IdentNode): Int = lookup(ident)._3

  def lookup(identifier: IdentNode): (TypeNode, IndexedSeq[TypeNode], Int) = {
    dict.getOrElse(identifier, throw new RuntimeException("Variable used but not in scope")) // TO DO: Change this?
  }

  def doesContain(identifier: IdentNode): Boolean = {
    try {
      lookup(identifier)
      true
    } catch {
      case _: Throwable => false
    }
  }
}
