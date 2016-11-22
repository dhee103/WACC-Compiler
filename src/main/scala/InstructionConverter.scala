object InstructionConverter {

  def translate(instructions: List[Instruction]): String = {

    instructions.map(inToString).mkString

  }

  def inToString(instr: Instruction) = instr.toString()

}
