import Condition._

object BuiltInFunctions {

  val lr = LinkRegister()
  val r0 = ResultRegister()
  val r1 = R1()
  val r2 = R2()
  val pc = ProgramCounter()

  val zero = ImmNum(0)
  val loadZero = LoadImmNum(0)
  val pushlr = Push(lr)
  val poppc = Pop(pc)
  val ltorg = Ltorg()

  var printFlag: Boolean = false

  def println(): List[Instruction] = {
    Label("p_print_ln") ::
      pushlr :: Move(r1, r0) :: Load(r0, LabelOp("p_print_ln")) ::
      Add(r0, r0, ImmNum(4)) :: BranchLink("puts") :: Move(r0, ImmNum(0)) ::
      BranchLink("fflush") :: poppc :: Nil
  }

  def printInt(): List[Instruction] = {
    Label("p_print_int") ::
      pushlr :: Move(r1, r0) :: Load(r0, LabelOp("p_print_int")) ::
      Add(r0, r0, ImmNum(4)) :: BranchLink("printf") :: Move(r0, ImmNum(0)) ::
      BranchLink("fflush") :: poppc :: Nil
  }

  def printString(): List[Instruction] = {
    Label("p_print_string") ::
      pushlr :: Move(r1, r0) :: Load(r1, StackReferenceRegister(r0)) :: Add (r2, r0, ImmNum(4)) :: Load(r0, LabelOp("p_print_string")) ::
      Add(r0, r0, ImmNum(4)) :: BranchLink("printf") :: Move(r0, ImmNum(0)) ::
      BranchLink("fflush") :: poppc :: Nil
  }

  def printBool(): List[Instruction] = {
    Label("p_print_bool") ::
      pushlr :: Compare(r0, ImmNum(0)) :: Load(r0, LabelOp("p_print_bool_t"),
      NE) :: Load(r0, LabelOp("p_print_bool_f"), EQ) :: Add(r0, r0, ImmNum(4)) ::
      BranchLink("printf") :: Move(r0, ImmNum(0)) :: BranchLink("fflush") ::
      poppc :: Nil
  }

  def printOverflowError(): List[Instruction] = {
    Label("p_throw_overflow_error") ::
      Load(r0, LabelOp("p_throw_overflow_error")) :: BranchLink("p_throw_runtime_error") :: Nil
  }

  def printRuntimeError(): List[Instruction] = {
    Label("p_throw_runtime_error") ::
    Move(r0, ImmNum(-1)) :: BranchLink("exit") :: Nil
  }

}
