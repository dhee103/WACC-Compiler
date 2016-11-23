object InstructionConverter {

  def translate(instructions: List[Instruction]): String = {

    (instructions.map(_.toString).map(_ + "\n")).mkString

  }

}
