object InstructionConverter {


  def functionConvert(func: Function): String = {

    null

  }

  def convertProgram(functionList: List[Function]):String = {

    functionList.map(functionConvert).mkString

  }


}
