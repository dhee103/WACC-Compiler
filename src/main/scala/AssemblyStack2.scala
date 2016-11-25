import collection.mutable.HashMap
import scala.collection.mutable._

class AssemblyStack2 {

  var states: MutableList[StackState] = MutableList[StackState]

  var virtualStackPointer = 0
  val sp = new StackPointer()
  //we use sp when we want to write instructions to move the actual stack pointer

  def subStackNewScope(spaceAlloc: Int): Instruction = {

    virtualStackPointer += spaceAlloc
    states += (new StackState(spaceAlloc, virtualStackPointer))
    new Sub(sp, sp, new ImmNum(spaceAlloc))

    //on the actual stack we grow down
    //here we will grow up
  }

  def addStackDestroyScope(spaceReturn: Int): Instruction = {

    virtualStackPointer = virtualStackPointer - spaceReturn

    states = states.dropRight(1)
    //todo does this actually get rid of the most recent entry?
    //todo or is it dropleft
    //todo nah i think its dropright

    new Add(sp, sp, new ImmNum(spaceReturn))

  }

  def getOffsetForIdentifier(identifier: IdentNode): Int  = {

    for (i: Int <- 0 to states.length - 1){

      val currentIndex: Int = states.length - i - 1

      //this is so we can go backwards because the latest added HashMap is at the end

      val currentState: StackState = states(currentIndex)

      if(currentState.dictContainsIdent(identifier)){

        //currentMap(identifier)  + (virtualStackPointer - originalSpLocations(currentIndex))
        currentState.getOffsetForIdentifier(identifier, virtualStackPointer)
        //you pass in the current stack pointer so it can
        //work out the difference between that and the origin pointer of the state

      }

    }

    //if we get heee then we never found the variable

    println("ERROR")

    throw new RuntimeException("Fatal error")

  }

  def addVariable(identifier: IdentNode): Unit = {

    states.last.getOffsetForIdentifier(identifier)

    //so add all the variables after you have alloced the space with the new scope
    //add in the correct order on the stack

  }

}
