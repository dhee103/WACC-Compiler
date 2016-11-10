import collection.mutable.HashMap

class SymbolTable(val _encTable: SymbolTable) {

  val encTable: SymbolTable =_encTable

  val dict: HashMap[IdentNode, TypeNode] = new HashMap[IdentNode, TypeNode]()

  def add (identifier: IdentNode, typeinfo: TypeNode): Unit
    = dict+=(identifier -> typeinfo)

  def lookup(identifier: IdentNode): TypeNode = dict(identifier)

  def lookupAll(identifier: IdentNode): TypeNode = {

    var result: TypeNode = null

    if(dict.isEmpty){

      null

      //Throw our own exception

    }else{

      if(dict.isDefinedAt(identifier)){

        dict(identifier)

      }else{

      encTable.lookupAll(identifier)

    }

  }
}


}
