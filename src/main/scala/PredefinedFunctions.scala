import Condition._
import Constants._

object PredefinedFunctions {

  var printFlag:       Boolean = false

  var arithmeticFlag:  Boolean = false

  var divisionFlag:    Boolean = false

  var freePairFlag:    Boolean = false

  var freeArrayFlag:   Boolean = false

  var runtimeFlag:     Boolean = false

  var nullPointerFlag: Boolean = false

  var checkArrayBoundsFlag: Boolean = false

  var readCharFlag: Boolean = false

  var readIntFlag: Boolean = false

  def println(): List[Instruction] = {
    Label("p_print_ln") ::
    Push(lr) ::
    Move(r1, r0) ::
    Load(r0, LabelOp("msg_p_print_ln")) ::
    Add(r0, r0, ImmNum(4)) ::
    BranchLink("puts") ::
    Load(r0, LoadImmNum(0)) ::
    BranchLink("fflush") ::
    Pop(pc) :: Nil
  }

  def printInt(): List[Instruction] = {
    Label("p_print_int") ::
    Push(lr) ::
    Move(r1, r0) ::
    Load(r0, LabelOp("msg_p_print_int")) ::
    Add(r0, r0, ImmNum(4)) ::
    BranchLink("printf") ::
    Load(r0, LoadImmNum(0)) ::
    BranchLink("fflush") ::
    Pop(pc) :: Nil
  }

  def printString(): List[Instruction] = {
    Label("p_print_string") ::
    Push(lr) ::
    Load(r1, RegisterStackReference(r0)) ::
    Add(r2, r0, ImmNum(4)) ::
    Load(r0, LabelOp("msg_p_print_string")) ::
    Add(r0, r0, ImmNum(4)) ::
    BranchLink("printf") ::
    Load(r0, LoadImmNum(0)) ::
    BranchLink("fflush") ::
    Pop(pc) :: Nil
  }

  def printBool(): List[Instruction] = {
    Label("p_print_bool") ::
    Push(lr) ::
    Compare(r0, ImmNum(0)) ::
    Load(r0, LabelOp("msg_p_print_bool_t"), NE) ::
    Load(r0, LabelOp("msg_p_print_bool_f"), EQ) ::
    Add(r0, r0, ImmNum(WORD_SIZE)) ::
    BranchLink("printf") ::
    Load(r0, LoadImmNum(0)) ::
    BranchLink("fflush") ::
    Pop(pc) :: Nil
  }

  def overflowError(): List[Instruction] = {
    Label("p_throw_overflow_error") ::
    Load(r0, LabelOp("msg_p_throw_overflow_error")) ::
    BranchLink("p_throw_runtime_error") :: Nil
  }

  def runtimeError(): List[Instruction] = {
    Label("p_throw_runtime_error") ::
    BranchLink("p_print_string") ::
    Load(r0, LoadImmNum(-1)) ::
    BranchLink("exit") :: Nil
  }

  def checkDivByZero(): List[Instruction] = {
    Label("p_check_divide_by_zero") ::
    Push(lr) ::
    Compare(r1, ImmNum(0)) ::
    Load(r0, LabelOp("msg_p_check_divide_by_zero"), EQ) ::
    BranchLink("p_throw_runtime_error", EQ) ::
    Pop(pc) :: Nil
  }

  def freePair(): List[Instruction] = {
    Label("p_free_pair") ::
    Push(lr) ::
    Compare(r0, ImmNum(0)) ::
    Load(r0, LabelOp("msg_null_check"), EQ) :: // add this label before
    StandardBranch("p_throw_runtime_error", EQ) ::
    Push(r0) ::
    Load(r0, RegisterStackReference(r0)) ::
    BranchLink("free") ::
    Load(r0, RegisterStackReference(sp)) ::
    Load(r0, RegisterStackReference(r0, WORD_SIZE)) ::
    BranchLink("free") ::
    Pop(r0) ::
    BranchLink("free") ::
    Pop(pc) :: Nil
  }

  def printReference(): List[Instruction] = {
    Label("p_print_reference") ::
    Push(lr) ::
    Move(r1, r0) ::
    Load(r0, LabelOp("msg_p_print_reference")) ::
    Add(r0, r0, ImmNum(WORD_SIZE)) ::
    BranchLink("printf") ::
    Load(r0, LoadImmNum(0)) ::
    BranchLink("fflush") ::
    Pop(pc) :: Nil
  }

  def checkNullPointer(): List[Instruction] = {
    Label("p_check_null_pointer") ::
    Push(lr) ::
    Compare(r0, ImmNum(0)) ::
    Load(r0, LabelOp("msg_null_check"), EQ) ::
    BranchLink("p_throw_runtime_error", EQ) ::
    Pop(pc) :: Nil
  }

  def checkArrayBounds(): List[Instruction] = {
    Label("p_check_array_bounds") ::
    Push(lr) ::
    Compare(r0, ImmNum(0)) ::
    Load(r0, LabelOp("msg_negative_index"), LT) ::
    BranchLink("p_throw_runtime_error", LT) ::
    Load(r1, RegisterStackReference(r4)) ::
    Compare(r0, r1) ::
    Load(r0, LabelOp("msg_index_too_large"), CS) ::
    BranchLink("p_throw_runtime_error", CS) ::
    Pop(pc) :: Nil
  }

  def readChar(): List[Instruction] = {
    Label("p_read_char") ::
    Push(lr) ::
    Move(r1, r0) ::
    Load(r0, LabelOp("msg_p_read_char")) ::
    Add(r0, r0, ImmNum(WORD_SIZE)) ::
    BranchLink("scanf") ::
    Pop(pc) :: Nil
  }

//  TODO: Abstract away commonality between readChar and readInt
  def readInt(): List[Instruction] = {
    Label("p_read_int") ::
    Push(lr) ::
    Move(r1, r0) ::
    Load(r0, LabelOp("msg_p_read_int")) ::
    Add(r0, r0, ImmNum(WORD_SIZE)) ::
    BranchLink("scanf") ::
    Pop(pc) :: Nil
  }


}
