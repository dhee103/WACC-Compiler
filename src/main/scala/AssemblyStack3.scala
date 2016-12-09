import scala.collection.mutable.MutableList
import Constants._

object AssemblyStack3 {
  private var stackFrames = new MutableList[StackFrame2]()

  // Adds a new StackFrame to list of stack frames
  // and returns instruction to set up stack frame
  // (allocating space for local variables)
  def createNewScope(localVars: List[IdentNode], params: List[IdentNode] = List())
  : List[Instruction] = {
    stackFrames += new StackFrame2(localVars, params)
    println(s"Scope created with ${localVars.size} local vars, ${params.size} params.") // DEBUG

    Push(lr) ::
    Push(fp) ::
    Move(fp, sp) ::
    Sub(sp, sp, ImmNum(WORD_SIZE * localVars.size)) :: Nil
  }

  def destroyNewestScope(): List[Instruction] = {
    val destroyedStack: StackFrame2 = stackFrames.last
    stackFrames = stackFrames.dropRight(1)

    Add(sp, sp, ImmNum(WORD_SIZE * destroyedStack.noOfLocalVars)) ::
    Pop(fp) :: Pop(pc) :: Nil
  }

  def getOffsetFor(ident: IdentNode): Int = {
    var offset: Int = 0
    offset -= WORD_SIZE * stackFrames.last.noOfLocalVars

    for (currentFrame <- stackFrames.reverse) {
      offset += WORD_SIZE * currentFrame.noOfLocalVars // realign fp, by going past local variables
      if (currentFrame.contains(ident)) {
        return offset + currentFrame.getOffsetFor(ident)
      }
      offset += WORD_SIZE * 2 // go past all "old" fps and lrs
      offset += WORD_SIZE * currentFrame.noOfParams // go past any params
    }

    throw new RuntimeException(s"Fatal error: Variable ${ident.name} with type ${ident.getType} not in scope.")
  }


}
