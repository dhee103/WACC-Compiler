trait Instruction {

}

case class Push(val src: Register) extends Instruction {

}

case class Pop(val dst: Register) extends Instruction {

}

case class Add(val dst: Register, val src1: Register, val src2: Register) extends Instruction {

}

case class Sub(val dst: Register, val src1: Register, val src2: Operand) extends Instruction {

}

case class Load(val dst: Register, val src: Operand) extends Instruction {

}

case class Store(val src: Register, val dst: Operand) extends Instruction {

}

case class Move(val dst: Register, val src: Operand) extends Instruction {

  //todo do we need movlt and movge

}

case class Mul(val dst: Register, val src1: Register, val src2: Register) extends Instruction {

}

case class Compare(val cmp1: Register, val cmp2: Operand) extends Instruction {

}


case class Function(val label: String) extends Instruction {

  var body: List[Instruction] = _

  //todo

}
