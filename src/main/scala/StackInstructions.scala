
class StackInstructions {

  def pushInstr(src: Register): Instruction = {
    //also generate the push instruction to do this
    null
  }

  //todo check whether its just a register type

  def popInstr(destination: Register): Instruction = {
    null
  }

  def getInstrOperand(offset: Int): StackReference = {
    null
  }

  def stackMinusInstr(size: Int): Instruction = {
    null
  }

  def stackAddInstr(size: Int): Instruction = {
    null
  }

}
