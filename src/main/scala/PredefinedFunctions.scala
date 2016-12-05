import Condition._
import Constants._

object PredefinedFunctions {

  val checkArrayBoundsFlag: Boolean = false

  var printFlag:       Boolean = false

  var arithmeticFlag:  Boolean = false

  var divisionFlag:    Boolean = false

  var freePairFlag:    Boolean = false

  var freeArrayFlag:   Boolean = false

  var nullPointerFlag: Boolean = false

  var runtimeFlag:     Boolean = false

  def println(): List[Instruction] = {
    Label("p_print_ln") ::
    Push(lr) ::
    Move(r1, r0) ::
    Load(r0, LabelOp("msg_p_print_ln")) ::
    Add(r0, r0, ImmNum(4)) ::
    BranchLink("puts") ::
    Move(r0, ImmNum(0)) ::
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
    Move(r0, ImmNum(0)) ::
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
    Move(r0, ImmNum(0)) ::
    BranchLink("fflush") ::
    Pop(pc) :: Nil
  }

  def printBool(): List[Instruction] = {
    Label("p_print_bool") ::
    Push(lr) ::
    Compare(r0, ImmNum(0)) ::
    Load(r0, LabelOp("msg_p_print_bool_t"), NE) ::
    Load(r0, LabelOp("msg_p_print_bool_f"), EQ) ::
    Add(r0, r0, ImmNum(4)) ::
    BranchLink("printf") ::
    Move(r0, ImmNum(0)) ::
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
    Move(r0, ImmNum(-1)) ::
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

  def printReference(): List[Instruction] = {
    Label("p_print_reference") ::
    Push(lr) ::
    Move(r1, r0) ::
    Load(r0, LabelOp("p_print_reference")) ::
    Add(r0, r0, ImmNum(4)) ::
    BranchLink("printf") ::
    Move(r0, ImmNum(0)) ::
    BranchLink("fflush") ::
    Pop(pc) :: Nil
  }

  def checkNullPointer(): List[Instruction] = {
    Label("p_check_null_pointer") ::
    Push(lr) ::
    Compare(r0, ImmNum(0)) ::
    Load(r0, LabelOp("p_check_null_pointer"), EQ) ::
    BranchLink("p_throw_runtime_error", EQ) ::
    Pop(pc) :: Nil
  }

  def checkArrayBounds(): List[Instruction] = {
    Label("p_check_array_bounds") ::
    Push(lr) ::
    Compare(r0, ImmNum(0)) ::
    Load(r0, LabelOp("out_of_bounds_negative_index"), LT) ::
    BranchLink("p_throw_runtime_error", LT) ::
    Load(r1, RegisterStackReference(r4)) ::
    Compare(r0, r1) ::
    Load(r0, LabelOp("out_of_bounds_index_too_large"), CS) ::
    BranchLink("p_throw_runtime_error", CS) ::
    Pop(pc) :: Nil
  }

//  p_check_array_bounds:
//  55		PUSH {lr}
//  56		CMP r0, #0
//  57		LDRLT r0, =msg_0
//  58		BLLT p_throw_runtime_error
//  59		LDR r1, [r4]
//  60		CMP r0, r1
//  61		LDRCS r0, =msg_1
//  62		BLCS p_throw_runtime_error
//  63		POP {pc}

}
