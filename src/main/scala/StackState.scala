class StackState {

  var StackSectionSize: Int = 0
  var originPointer: Int = 0
  var dict: HashMap[IdentNode, Int] = new HashMap[IdentNode, Int]()

  var pushTracker: Int = 0

  def StackState(size: int, currentSP: int): Unit = {

    StackSectionSize = size
    originPointer = currentSP
    //this will be the SP after space is allocated for the variables

  }

  def addVariable(identifier: IdentNode): Unit = {

    dict += (identifier -> (StackSectionSize - pushTracker)

  pushTracker = pushTracker + 4

  }

  def getOffsetForIdentifier(identifier: IdentNode, currentSP: Int): Int = {

    dict(identifier) + (currentSP - originPointer)

  }

  def dictContainsIdent(identifier: IdentNode): Boolean = dict.isDefinedAt(identifier)
}
