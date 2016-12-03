import scala.collection.mutable.MutableList
import Constants._

object AssemblyStack3 {
  private var stackFrames = new MutableList[StackFrame2]()

  // Adds a new StackFrame to list of stack frames
  // and returns instruction to set up stack frame
  // (allocating space for local variables)
  def createNewScope(localVars: List[IdentNode]): List[Instruction] = {
    stackFrames += new StackFrame2(localVars)

    Push(fp) ::
    Move(fp, sp) ::
    Sub(sp, sp, ImmNum(WORD_SIZE * localVars.size)) :: Nil
  }

  def destroyNewestScope(): List[Instruction] = {
    val destroyedStack: StackFrame2 = stackFrames.last
    stackFrames = stackFrames.dropRight(1)

    Add(sp, sp, ImmNum(destroyedStack.size)) ::
    Pop(fp) :: Nil
  }

  def getOffsetFor(ident: IdentNode): Int = {
    var offset: Int = 0

    for (currentFrame <- stackFrames.reverse) {
      if (currentFrame.contains(ident)) {
        return offset + currentFrame.getOffsetFor(ident)
      }
      offset += WORD_SIZE // go past all "old" fps
      offset += WORD_SIZE * currentFrame.size // go past all other local vars
    }

    throw new RuntimeException("Fatal error: Variable not in scope.")
  }


}
