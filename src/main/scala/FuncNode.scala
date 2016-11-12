

/**
  * Created by dsg115 on 12/11/16.
  */
class FuncNode(val _typeSignature: TypeNode, val _identifier: IdentNode,
               val _paramList: ParamListNode, val _statement: StatNode)
  extends AstNode {

  var typeSignature: TypeNode = _typeSignature
  var identifier: IdentNode = _identifier
  var paramList: ParamListNode = _paramList
  var statement: StatNode = _statement



}
