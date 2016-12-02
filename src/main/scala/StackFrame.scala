import scala.collection.mutable.HashMap

// Purpose of StackFrame class is to map IdentNodes to the offset from
// the frame pointer of that stack frame
class StackFrame(var stackSectionSize: Int, var originPointer: Int) {

  var dict: HashMap[IdentNode, Int] = new HashMap[IdentNode, Int]()


  def addVariable(identifier: IdentNode): Unit = {


    //todo should only be able to add variables within the allocate space, not too many
    //todo add a check for this?
  }

  def getOffsetForIdentifier(identifier: IdentNode, currentSP: Int): Int = {


  }

  def dictContainsIdent(identifier: IdentNode): Boolean = {
    dict.isDefinedAt(identifier)
  }
}

/////

class StackFrame2(private val identifiers: List[IdentNode]) {
  private val offsetMap = new HashMap[IdentNode, Int]()
  def size: Int = offsetMap.size

  for ( (name, index)  <- identifiers.zipWithIndex)
    offsetMap += (name -> (index * -4))

  def getOffsetFor(ident: IdentNode): Int = {
    offsetMap.getOrElse(ident,
      throw new RuntimeException("Fatal Error: Variable not found in " +
        "getOffsetFor. Semantic checking should prevent this."))
  }

  def doesContain(ident: IdentNode): Boolean = {
    offsetMap.isDefinedAt(ident)
  }

}
