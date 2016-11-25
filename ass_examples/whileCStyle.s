	.data
	
	msg_0:
		.word 82
		.ascii	"OverflowError: the result is too small/large to store in a 4-byte signed-integer.\n"
	msg_1:
		.word 3
		.ascii	"%d\0"
	msg_2:
		.word 1
		.ascii	"\0"
	msg_3:
		.word 5
		.ascii	"%.*s\0"
	
	.text
	
	.global main
	main:
		PUSH {lr}
		SUB sp, sp, #4
		LDR r0, =5
		STR r0, [sp]
		B L1
	L0:
		LDR r0, [sp]
		PUSH {r0}
		LDR r0, =1
		MOV r1, r0
		POP {r0}
		ADDS r0, r0, r1
		BLVS p_throw_overflow_error
		STR r0, [sp]
		LDR r0, [sp]
		BL p_print_int
		BL p_print_ln
	L1:
		LDR r0, [sp]
		PUSH {r0}
		LDR r0, =10
		MOV r1, r0
		POP {r0}
		CMP r0, r1
		BGE L0
		ADD sp, sp, #4
		MOV r0, #0
		POP {pc}
	p_throw_overflow_error:
		LDR r0, =msg_0
		BL p_throw_runtime_error
	p_print_int:
		PUSH {lr}
		MOV r1, r0
		LDR r0, =msg_1
		ADD r0, r0, #4
		BL printf
		MOV r0, #0
		BL fflush
		POP {pc}
	p_print_ln:
		PUSH {lr}
		LDR r0, =msg_2
		ADD r0, r0, #4
		BL puts
		MOV r0, #0
		BL fflush
		POP {pc}
	p_throw_runtime_error:
		BL p_print_string
		MOV r0, #-1
		BL exit
	p_print_string:
		PUSH {lr}
		LDR r1, [r0]
		ADD r2, r0, #4
		LDR r0, =msg_3
		ADD r0, r0, #4
		BL printf
		MOV r0, #0
		BL fflush
		POP {pc}
