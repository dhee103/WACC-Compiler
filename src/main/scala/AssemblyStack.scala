import collection.mutable.HashMap
import scala.collection.mutable.MutableList

class AssemblyStack {

  var dicts: MutableList[HashMap[IdentNode, Int]] = MutableList[HashMap[IdentNode, Int]]()

  var stackSectionSizes: MutableList[Int] = MutableList[Int]()

  var originalSpLocations: MutableList[Int] = MutableList[Int]()

//  originalSpLocations += 0

  //stackSectionSizes += 0

  //dicts += new HashMap[IdentNode, Int]()

  var virtualStackPointer = 0
  var pushTracker = 0
  //use pushTracker so know where to put next variable

  val sp = new StackPointer()

  def subStackNewScope(spaceAlloc: Int): Instruction = {

    pushTracker = 0

    virtualStackPointer += spaceAlloc

    dicts += new HashMap[IdentNode, Int]()
    stackSectionSizes += spaceAlloc
    originalSpLocations += virtualStackPointer
    new Sub(sp, sp, new ImmNum(spaceAlloc))

    //on the stack we grow down
    //here we will grow up

    //scope is not changing so do not need another mapping
  }

  def addStackDestroyScope(spaceReturn: Int): Instruction = {

    virtualStackPointer = virtualStackPointer - spaceReturn

    stackSectionSizes = stackSectionSizes.dropRight(1)
    dicts = dicts.dropRight(1)
    originalSpLocations = originalSpLocations.dropRight(1)

    new Add(sp, sp, new ImmNum(spaceReturn))

  }

  def getOffsetForIdentifier(identifier: IdentNode): Int  = {


    //this is the offset from the current stack pointer
    //if its in the current scope//just get the offset from where the sp waccPa
    //add it to offset of current sp to sp at start


    for (i: Int <- 0 to dicts.length - 1){

      val currentIndex: Int = dicts.length - i - 1

      //this is so we can go backwards because the latest added HashMap is at the end

      val currentMap: HashMap[IdentNode, Int] = dicts(currentIndex)

      if(currentMap.isDefinedAt(identifier)){

        currentMap(identifier)  + (virtualStackPointer - originalSpLocations(currentIndex))

      }

    }

    //if we get hre then it is afata errors

    println("ERROR")

    throw new RuntimeException("Fatal error")

  }

  def addVariable(identifier: IdentNode): Unit = {

    //add a variable with offset relative to current sp
    //so e.g first variable in section goes 4 above st?
    //then modify pushTracker

    dicts.last += (identifier -> (stackSectionSizes.last - pushTracker))

    pushTracker = pushTracker + 4

  }

}
