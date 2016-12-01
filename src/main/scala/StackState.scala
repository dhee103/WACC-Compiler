import scala.collection.mutable.HashMap

class StackState(var stackSectionSize: Int, var originPointer: Int) {

  var dict: HashMap[IdentNode, Int] = new HashMap[IdentNode, Int]()

  var pushTracker: Int = 0

  def addVariable(identifier: IdentNode): Unit = {

    dict += (identifier -> (stackSectionSize - pushTracker))

    pushTracker += 4

    //todo should only be able to add variables within the allocate space, not too many
    //todo add a check for this?
  }

  def getOffsetForIdentifier(identifier: IdentNode, currentSP: Int): Int = {

    dict(identifier) + (currentSP - originPointer)

  }

  def dictContainsIdent(identifier: IdentNode): Boolean = dict.isDefinedAt(identifier)
}
