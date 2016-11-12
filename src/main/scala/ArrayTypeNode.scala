

/**
  * Created by dsg115 on 12/11/16.
  */
class ArrayTypeNode(val _elemType: TypeNode) extends PairElemTypeNode with TypeNode {

  val elemType: TypeNode = _elemType
}
