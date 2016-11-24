import collection.mutable.HashMap

class SymbolTable(val encTable: Option[SymbolTable]) {

  val dict: HashMap[IdentNode, TypeNode] = new HashMap[IdentNode, TypeNode]()

  def add(identifier: IdentNode, typeinfo: TypeNode): Unit
    = dict += (identifier -> typeinfo)

  def lookup(identifier: IdentNode): TypeNode = {
    dict.getOrElse(identifier, ErrorTypeNode())
  }

  def lookupAll(identifier: IdentNode): TypeNode = {

    encTable match {
      case None => lookup(identifier)
      case Some(parentTable: SymbolTable) => dict.getOrElse(identifier, parentTable.lookupAll(identifier))
    }

  }

  def doesContain(identifier: IdentNode): Boolean = {
      lookup(identifier) != ErrorTypeNode()
  }

}
