parser grammar WaccParser;

options {
  tokenVocab=WaccLexer;
}

// EOF indicates that the program must consume to the end of the input.
prog: BEGIN (func)* stat END EOF ;

func: type ident LPAREN (param_list)? RPAREN IS stat END ;

param_list: param (COMMA param)* ;

param: type ident ;

stat: SKIP_                                                 #SkipStat
    | type ident EQUAL assign_rhs                           #Declaration
    | assign_lhs EQUAL assign_rhs                           #Assignment
    | FREE expr                                             #Free
    | RETURN expr                                           #Return
    | EXIT expr                                             #Exit
    | PRINT expr                                            #Print
    | PRINTLN expr                                          #Println
    | IF expr THEN stat ELSE stat FI                        #If
    | WHILE expr DO stat DONE                               #While
    | BEGIN stat END                                        #NewBegin
    | stat SEMICOLON stat                                   #Sequence
    ;

assign_lhs: ident                                           #IdentLHS
          | array_elem                                      #ArrayElemLHS
          | pair_elem                                       #PairElemLHS
          ;

assign_rhs: expr                                            #ExprL
          | array_liter                                     #ArrayLiteral
          | NEWPAIR LPAREN expr COMMA expr RPAREN           #NewPair
          | pair_elem                                       #PairElem
          | CALL ident LPAREN (arg_list)? RPAREN            #Call
          ;

arg_list: expr (COMMA expr)* ;

pair_elem: FIRST expr                                       #Fst
         | SECOND expr                                      #Snd
         ;

type: base_type                                             #BaseType
    | type LBRACKET RBRACKET                                #TypeL
    | pair_type                                             #PairType
    ;

base_type: INT_TYPE                                         #Int
         | BOOL_TYPE                                        #Bool
         | CHAR_TYPE                                        #Char
         | STRING_TYPE                                      #String
         ;

pair_type: PAIR_TYPE LPAREN pair_elem_type COMMA pair_elem_type RPAREN ;

pair_elem_type: base_type                                   
              | type LBRACKET RBRACKET
              | PAIR_TYPE
              ;

expr: int_liter                                             #IntLiteral
    | bool_liter                                            #BoolLiteral
    | char_liter                                            #CharLiteral
    | str_liter                                             #StringLiteral
    | pair_liter                                            #PairLiteral
    | ident                                                 #IdentL
    | array_elem                                            #ArrayElem
    | unary_oper expr                                       #UnaryOperation
    | expr binary_op1 expr                                  #BinaryOp1
    | expr (PLUS) expr                                      #Plus
    | expr binary_op3 expr                                  #BinaryOp3
    | expr binary_op4 expr                                  #BinaryOp4
    | expr (LOGICAL_AND) expr                               #LogicalAnd
    | expr (LOGICAL_OR) expr                                #LogicalOr
    | LPAREN expr RPAREN                                    #Parens
    ;

unary_oper: EXCLAMATION                                     #LogicalNot
          | MINUS                                           #Negative
          | LENGTH                                          #Len
          | ORD                                             #Ord
          | CHR                                             #Chr
          ;

binary_op1: MULTIPLY                                        #MulOperation
          | DIVIDE                                          #DivOperation
          | MOD                                             #Mod
          ;

binary_op3: GREATER_THAN                                    #GreaterThan
          | GREATER_EQUAL                                   #GreaterEqual
          | LESS_THAN                                       #LessThan
          | LESS_EQUAL                                      #LessEqual
          ;

binary_op4: DOUBLE_EQUAL                                    #DoubleEqual
          | NOT_EQUAL                                       #NotEqual
          ;

ident: ID ;

array_elem: ident (LBRACKET expr RBRACKET)+ ;

int_liter: INT_LITERAL ;

bool_liter: TRUE_LITERAL
          | FALSE_LITERAL
          ;

char_liter: CHAR_LITERAL ;

str_liter: STR_LITERAL ;

array_liter: LBRACKET ((expr) (COMMA expr)*)? RBRACKET ;

pair_liter: NULL ;