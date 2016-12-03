object Constants {
  //todo: import this everywhere
  val WORD_SIZE = 4

  val lr = LinkRegister()
  val r0 = ResultRegister()
  val r1 = R1()
  val r2 = R2()
  val pc = ProgramCounter()
  val fp = FramePointer()
  val sp = StackPointer()

  val zero = ImmNum(0)
  val loadZero = LoadImmNum(0)
  val pushlr = Push(lr)  //todo: remove these unneeded variables
  val poppc = Pop(pc)
  val ltorg = Directive("ltorg")
}
