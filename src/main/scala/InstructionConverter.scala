object InstructionConverter {


  def functionConvert(func: Function): String = {

    null

  }

  def convertProgram(functionList: List[Function]):String = {

    functionList.map(functionConvert).mkString

  }

  def convertMov(mov: Move): String = {

    //mov.src should not be a function call - serious error is fo


    val destination: String = mov.dst.toString()

    val operand: String = mov.src.toString()

    "mov" + destination + " , " + operand



  }
}
