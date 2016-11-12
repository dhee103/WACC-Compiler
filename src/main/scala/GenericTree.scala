

/**
  * Created by dsg115 on 12/11/16.
  */
trait GenericTree[T] {

  var head: TreeNode[T]

  def add(node: TreeNode[T]): Unit

  def toPreOrderList: Array[T]

  def toInOrderList: Array[T]

  def toPostOrderList: Array[T]

}
