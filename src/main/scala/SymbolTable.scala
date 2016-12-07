import collection.mutable.HashMap

// A symbol table maps NAMES of identifiers to their TYPES
class SymbolTable(val encTable: Option[SymbolTable]) {

  val dict: HashMap[String, TypeNode] = new HashMap[String, TypeNode]()

  def add(identifier: IdentNode, typeInfo: TypeNode): Unit = {
    dict += (identifier.name -> typeInfo)
  }

  def lookup(identifier: IdentNode): TypeNode = {
    dict.getOrElse(identifier.name, ErrorTypeNode())
  }

  def lookupAll(identifier: IdentNode): TypeNode = {
    encTable match {
      case None => lookup(identifier)
      case Some(parentTable: SymbolTable) => dict.getOrElse(identifier.name, parentTable.lookupAll(identifier))
    }
  }

  def doesContain(identifier: IdentNode): Boolean = {
      lookup(identifier) != ErrorTypeNode()
  }

  def size: Int = {
    dict.size
  }

  def symbols: List[IdentNode] = {
    (for ((name, typeNode) <- dict) yield IdentNode(name, Some(typeNode))).to[collection.immutable.List]
  }

}
