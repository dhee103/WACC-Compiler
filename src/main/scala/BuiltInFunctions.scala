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

  // msg_1:
	// 	.word 3
	// 	.ascii	"%d\0"
	// msg_2:
	// .word 1
	// 	.ascii	"\0"
	// msg_3:
	// 	.word 5
	// 	.ascii	"%.*s\0"


  def println(): List[Instruction] = {
    Labels.addDataMsgLabel("\\0", "pritntln")
    // pushlr :: Move(r1, r0) :: Load(r0, )
    Nil
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
	// 	LDR r0, =msg_0
	// 	ADD r0, r0, #4
	// 	BL printf
	// 	MOV r0, #0
	// 	BL fflush
	// 	POP {pc}
	// p_print_ln:
	// 	PUSH {lr}
	// 	LDR r0, =msg_1
  // 	ADD r0, r0, #4
	// 	BL puts
	// 	MOV r0, #0
	// 	BL fflush
	// 	POP {pc}

}
