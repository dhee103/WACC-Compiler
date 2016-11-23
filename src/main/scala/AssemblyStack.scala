import collection.mutable.HashMap
import scala.collection.mutable._

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
  val bp = new BasePointer()


  def subStackNewScope(spaceAlloc: Int): Instruction = {

    pushTracker = 0

    virtualStackPointer = virtualStackPointer + spaceAlloc

    dicts += new HashMap[IdentNode, Int]()
    stackSectionSizes += spaceAlloc
    originalSpLocations += virtualStackPointer
    new Sub(sp, sp, new ImmNum(spaceAlloc))

    //on the stack we grow ddown
    //here we will grow up

    //scope is not changing so do not need another mapping
  }

  def addStackDestroyScope(spaceReturn: Int): Instruction = {

    virtualStackPointer = virtualStackPointer - spaceReturn

    stackSectionSizes = stackSectionSizes.dropRight(1)
    dicts = dicts.dropRight(1)
    originalSpLocations = originalSpLocations.dropRight(1)

    new Add(sp, sp, new ImmNum(spaceReturn))

    //pushTracker = 0

    //what happens to pushteacker??

  }

  def getOffsetForIdentifier(identifier: IdentNode): Int  = {


    //this is the offset from the current stack pointer
    //if its in the current scope//just get the offset from where the sp waccPa
    //add it to offset of current sp to sp at start


    for (i: Int <- 0 to 4){

      val currentIndex: Int = dicts.length - i - 1

      val currentMap: HashMap[IdentNode, Int] = dicts(currentIndex)

      if(currentMap.isDefinedAt(identifier)){

        currentMap(identifier)  + (virtualStackPointer - originalSpLocations(currentIndex))

      }

    }

    //if we get hre then it is afata errors

    println("ERROR")

    5

  }



  def addVariable(identifier: IdentNode): Unit = {

    //add a variable with offset relative to current sp
    //so e.g first variable in section goes 4 above st?
    //then modify pushTracker#

    dicts.last += (identifier -> (stackSectionSizes.last - pushTracker))

    // + or minus? cause we are growing up>??
    pushTracker = pushTracker + 4

  }



}
