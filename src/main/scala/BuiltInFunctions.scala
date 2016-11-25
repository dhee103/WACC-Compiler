import scala.collection.mutable._

object BuiltInFunctions {

  val lr = LinkRegister()
  val r0  = ResultRegister()
  val r1 = R1()
  val pc  = ProgramCounter()

  val zero = ImmNum(0)
  val loadZero = LoadImmNum(0)
  val pushlr = Push(lr)
  val poppc = Pop(pc)
  val ltorg = Ltorg()

// make a hashmap of all the built in messages
// generate label from "Labels.scala" as appropriate within functions


// add all labels to hashmap
// use lookup - allows it to not have lots of the same label



  def println(): List[Instruction] = {
    Labels.addDataMsgLabel("\\0", "println")
    pushlr :: Move(r1, r0) :: Load(r0, LabelOp("println")) ::
    Add(r0, r0, ImmNum(4)) :: BranchLink("puts") :: Move(r0, ImmNum(0)) ::
    BranchLink("fflush") :: poppc :: Nil
  }

  def printInt(): List[Instruction] = {
    Labels.addDataMsgLabel("%d\\0", "printInt")
    Nil
  }

  def printString(): List[Instruction] = {
    Labels.addDataMsgLabel("%.*s\\0", "printString")
    Nil
  }

  // p_print_int:
	// 	PUSH {lr}
	// 	MOV r1, r0
	// 	LDR r0, =msg_0 Load(r0, Labels.getDataMsgLabel())
	// 	ADD r0, r0, #4
	// 	BL printf
	// 	MOV r0, #0
	// 	BL fflush
	// 	POP {pc}


}
