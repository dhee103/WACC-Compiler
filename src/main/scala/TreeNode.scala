class TreeNode[T] (val _item: T, val _left: TreeNode[T],
 val _right: TreeNode[T], val _parent: TreeNode[T]){

   private var item: T = _item
   private var left: TreeNode[T] = _left
   private var right: TreeNode[T] = _right
   private var parent: TreeNode[T] = _parent

   def setItem(value : T): Unit = {
      item = value
    }

    def getItem = item

    def getLeftNode = left

    def getRightNode = right

    def getParentNode = parent

    def setLeft(newNode: TreeNode[T]): Unit
      = left = newNode

    def setRight(newNode: TreeNode[T]): Unit
      = right = newNode

    def print: T
      = item

}

trait GenericTree[T] {

  var head: TreeNode[T]

  def add(node: TreeNode[T]): Unit
  def toPreOrderList: Array[T]
  def toInOrderList: Array[T]
  def toPostOrderList: Array[T]

}
