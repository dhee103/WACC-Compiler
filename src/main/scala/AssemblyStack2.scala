import scala.collection.mutable.MutableList

object AssemblyStack2 {

  var states = MutableList[StackState](new StackState(0, 0))

  var virtualStackPointer = 0
  val sp = StackPointer()
  //we use sp when we want to write instructions to move the actual stack
  // pointer

  def subStackNewScope(spaceAlloc: Int): Instruction = {

    virtualStackPointer += spaceAlloc
    states += new StackState(spaceAlloc, virtualStackPointer)
    Sub(sp, sp, ImmNum(spaceAlloc))

    //on the actual stack we grow down
    //here we will grow up
  }

  def addStackDestroyScope(spaceReturn: Int): Instruction = {

    virtualStackPointer = virtualStackPointer - spaceReturn

    states = states.dropRight(1)
    //todo does this actually get rid of the most recent entry?
    //todo or is it dropleft
    //todo nah i think its dropright

    Add(sp, sp, ImmNum(spaceReturn))

  }

  def getOffsetForIdentifier(identifier: IdentNode): Int = {

    for (currentState <- states.reverse) {


      if (currentState.dictContainsIdent(identifier)) {

        //currentMap(identifier)  + (virtualStackPointer -
        // originalSpLocations(currentIndex))
        return currentState.getOffsetForIdentifier(identifier,
          virtualStackPointer)
        //you pass in the current stack pointer so it can
        //work out the difference between that and the origin pointer of the
        // state

      }
    }

    // Should be impossible to get here
    // Semantic checking ensures that variables are in scope
    throw new RuntimeException("Fatal error: Variable not in scope.")
  }

  def addVariable(identifier: IdentNode): Unit = {

    states.last.addVariable(identifier)

    //todo add a check to see if there is actually any states in the list
    //todo e.g when you do main you actually need to do a scope for mapping

    //so add all the variables after you have alloced the space with the
    // new scope
    //add in the correct order on the stack

  }

}
