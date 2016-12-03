import Condition._
import Constants._

object BuiltInFunctions {

  var printFlag: Boolean = false

  var arithmeticFlag: Boolean = false

  var freePairFlag: Boolean = false

  var freeArrayFlag: Boolean = false

  def println(): List[Instruction] = {
    Label("p_print_ln") ::
      pushlr :: Move(r1, r0) :: Load(r0, LabelOp("msg_p_print_ln")) ::
      Add(r0, r0, ImmNum(4)) :: BranchLink("puts") :: Move(r0, ImmNum(0)) ::
      BranchLink("fflush") :: poppc :: Nil
  }

  def printInt(): List[Instruction] = {
    Label("p_print_int") ::
      pushlr :: Move(r1, r0) :: Load(r0, LabelOp("msg_p_print_int")) ::
      Add(r0, r0, ImmNum(4)) :: BranchLink("printf") :: Move(r0, ImmNum(0)) ::
      BranchLink("fflush") :: poppc :: Nil
  }

  def printString(): List[Instruction] = {
    Label("p_print_string") ::
      pushlr ::
      Load(r1, RegisterStackReference(r0)) ::
      Add(r2, r0, ImmNum(4)) ::
      Load(r0, LabelOp("msg_p_print_string")) ::
      Add(r0, r0, ImmNum(4)) ::
      BranchLink("printf") ::
      Move(r0, ImmNum(0)) ::
      BranchLink("fflush") :: poppc :: Nil
  }

  def printBool(): List[Instruction] = {
    Label("p_print_bool") ::
      pushlr :: Compare(r0, ImmNum(0)) :: Load(r0, LabelOp("msg_p_print_bool_t"),
      NE) :: Load(r0, LabelOp("msg_p_print_bool_f"), EQ) :: Add(r0, r0, ImmNum(4)) ::
      BranchLink("printf") :: Move(r0, ImmNum(0)) :: BranchLink("fflush") ::
      poppc :: Nil
  }

  def printOverflowError(): List[Instruction] = {
    Label("p_throw_overflow_error") ::
      Load(r0, LabelOp("msg_p_throw_overflow_error")) :: BranchLink("p_throw_runtime_error") :: Nil
  }

  def printRuntimeError(): List[Instruction] = {
    Label("p_throw_runtime_error") ::
      Move(r0, ImmNum(-1)) :: BranchLink("exit") :: Nil
  }

  def printFreePair(): List[Instruction] = {
    Label("p_free_pair") ::
    Push(lr) ::
    Compare(r0, ImmNum(0)) ::
    Load(r0, LabelOp("p_free_pair"), EQ) :: // add this label before
    StandardBranch("p_throw_runtime_error", EQ) ::
    Push(r0) ::
    Load(r0, RegisterStackReference(r0)) ::
    BranchLink("free") ::
    Load(r0, RegisterStackReference(sp)) ::
    Load(r0, RegisterStackReference(r0, 4)) ::
    BranchLink("free") ::
    Pop(r0) ::
    BranchLink("free") ::
    Pop(pc) :: Nil
  }

}
