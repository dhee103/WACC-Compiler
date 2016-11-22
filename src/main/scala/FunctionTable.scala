import collection.mutable.HashMap

object FunctionTable {
// TODO: consider having tuple of TypeNode and paramList
// TODO: consider having an trait/abstract class for functionTable & SymbolTable
  val dict = new HashMap[IdentNode, (TypeNode, IndexedSeq[TypeNode])]()

  def add(func: FuncNode): Unit = {
    val identifier: IdentNode = func.identifier
    val returnType: TypeNode = func.typeSignature
    val paramTypes: IndexedSeq[TypeNode] = for (param <- func.paramList.params)
      yield param.variableType

    if (dict.contains(identifier)) {
      val name = identifier.name
      SemanticErrorLog.add(s"[Semantic Error] Attempted to redefine function $name.")
    } else {
      dict += (identifier -> (returnType, paramTypes))
    }
  }

  def getReturnType(ident: IdentNode) = lookup(ident)._1

  def getParamTypes(ident: IdentNode): IndexedSeq[TypeNode] = lookup(ident)._2

  // def add(identifier: IdentNode, typeinfo: (TypeNode, IndexedSeq[TypeNode])): Unit
  //   = dict += (identifier -> typeinfo)


  def lookup(identifier: IdentNode): (TypeNode, IndexedSeq[TypeNode]) = {
    dict.getOrElse(identifier, throw new RuntimeException("Variable used but not in scope"))
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
