trait Register extends Operand{

}

case class StackPointer() extends Register {
  
}

case class Linkregister() extends Register {

}

case class ProgramCounter() extends Register {

}
