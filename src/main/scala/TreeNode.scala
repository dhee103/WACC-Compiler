/**
  * Created by dsg115 on 12/11/16.
  */
class TreeNode[T](val _item: T, val _children: Array[TreeNode[T]], val _parent: TreeNode[T]) {

  private var item: T = _item

  private var parent: TreeNode[T] = _parent

  private var children: Array[TreeNode[T]] = _children

  def setItem(value: T): Unit = {
    item = value
  }

  def getItem = item

  def getParentNode = parent

  def print: T
  = item

}
