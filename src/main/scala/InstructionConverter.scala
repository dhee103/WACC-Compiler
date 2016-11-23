object InstructionConverter {

  def translate(instructions: List[Instruction]): String = {

    (instructions.map(inToString).map(addNewLine)).mkString

  }

  def inToString(instr: Instruction) = instr.toString()

  def addNewLine(instruction: String): String = instruction + "\n"

}
