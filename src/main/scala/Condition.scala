object Condition extends Enumeration {
  type  Condition = Value
  val EQ, NEQ, NE, VS, VC, LS, HI, LT, GT  = Value
  val AL = Value("")
}

/*

EQ    Equal
NE    Not equal
CS    Carry set (identical to HS)
HS    Unsigned higher or same (identical to CS)
CC    Carry clear (identical to LO)
LO    Unsigned lower (identical to CC)
MI    Minus or negative result
PL    Positive or zero result
VS    Overflow
VC    No overflow
HI    Unsigned higher
LS    Unsigned lower or same
GE    Signed greater than or equal
LT    Signed less than
GT    Signed greater than
LE    Signed less than or equal
AL    Always (this is the default)

*/
