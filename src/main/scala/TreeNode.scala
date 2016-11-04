class TreeNode[T] (val: _item: T, val _left : Node[T],
 val _right: Node[T], val _parent: Node[T]){

   private var item: T = _item
   private var left: Node[T] = _left
   private var right: Node[T] = _right
   private var parent: Node[t] = _parent

   def setItem(value : T): Unit = {
      item = value
    }

    def getItem = item

    def getLeftNode = left

    def getRightNode = right

    def getParentNode = parent

    def setLeft(newNode : Node[T]): Unit
      = left = newNode

    def setRight(newNode : Node[T]): Unit
      = right = newNode

    def print: T
      = item

}

trait GenericTree[T] {

  private var head: Node[T]

  def add(node : Node[T]): Unit
  def toPreOrderList: Array[T]
  def toInOrderList: Array[T]
  def toPostOrderListL Array[T]
  
}
