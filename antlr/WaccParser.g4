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
    | READ assign_lhs                                       #Read
    | FREE expr                                             #Free
    | RETURN expr                                           #Return
    | EXIT expr                                             #Exit
    | PRINT expr                                            #Print
    | PRINTLN expr                                          #Println
    | IF expr THEN stat ELSE stat FI                        #IfThenElse
    | IF expr THEN stat FI                                  #IfThen
    | WHILE expr DO stat DONE                               #While
    | BEGIN stat END                                        #NewBegin
    | stat SEMICOLON stat                                   #Sequence
    | BREAK                                                 #Break
    ;

assign_lhs: ident                                           #IdentLHS
          | array_elem                                      #ArrayElemLHS
          | pair_elem                                       #PairElemLHS
          ;

assign_rhs: expr                                            #ExprRHS
          | array_liter                                     #ArrayLiteralRHS
          | NEWPAIR LPAREN expr COMMA expr RPAREN           #NewPairRHS
          | pair_elem                                       #PairElemRHS
          | CALL ident LPAREN (arg_list)? RPAREN            #CallRHS
          ;

arg_list: expr (COMMA expr)* ;

pair_elem: FIRST expr                                       #FstElem
         | SECOND expr                                      #SndElem
         ;

type: base_type                                             #BaseType
    | type LBRACKET RBRACKET                                #ArrayType
    | pair_type                                             #PairType
    ;

base_type: INT_TYPE                                         #IntType
         | BOOL_TYPE                                        #BoolType
         | CHAR_TYPE                                        #CharType
         | STRING_TYPE                                      #StringType
         ;

pair_type: PAIR_TYPE LPAREN pair_elem_type COMMA pair_elem_type RPAREN ;

pair_elem_type: base_type                                   #BaseTypePairElem
              | type LBRACKET RBRACKET                      #ArrayTypePairElem
              | PAIR_TYPE                                   #PairTypePairElem
              ;

expr: int_liter                                             #IntLiteral
    | bool_liter                                            #BoolLiteral
    | char_liter                                            #CharLiteral
    | str_liter                                             #StringLiteral
    | pair_liter                                            #PairLiteral
    | ident                                                 #IdentExpr
    | array_elem                                            #ArrayElemExpr
    | unary_oper expr                                       #UnaryOperation
    | expr binary_op1 expr                                  #BinaryOperation1
    | expr binary_op2 expr                                  #BinaryOperation2
    | expr binary_op3 expr                                  #BinaryOperation3
    | expr binary_op4 expr                                  #BinaryOperation4
    | expr binary_op5 expr                                  #BinaryOperation5
    | expr binary_op6 expr                                  #BinaryOperation6
    | LPAREN expr RPAREN                                    #Parens
    ;

unary_oper: EXCLAMATION                                     //#LogicalNot
          | MINUS                                           //#Negative
          | LENGTH                                          //#Len
          | ORD                                             //#Ord
          | CHR                                             //#Chr
          ;

binary_op1: MULTIPLY                                        //#MulOperation
          | DIVIDE                                          //#DivOperation
          | MOD                                             //#Mod
          ;

binary_op2: PLUS
          | MINUS
          ;

binary_op3: GREATER_THAN                                    //#GreaterThan
          | GREATER_EQUAL                                   //#GreaterEqual
          | LESS_THAN                                       //#LessThan
          | LESS_EQUAL                                      //#LessEqual
          ;

binary_op4: DOUBLE_EQUAL                                    //#DoubleEqual
          | NOT_EQUAL                                       //#NotEqual
          ;

binary_op5: LOGICAL_AND ;

binary_op6: LOGICAL_OR ;

ident: ID  #Identifier
     ;

array_elem: ident (LBRACKET expr RBRACKET)+  #ArrayElem
          ;

int_liter: (PLUS | MINUS)?  INT_LITERAL ;

bool_liter: TRUE_LITERAL
          | FALSE_LITERAL
          ;

char_liter: CHAR_LITERAL ;

str_liter: STR_LITERAL ;

array_liter: LBRACKET ((expr) (COMMA expr)*)? RBRACKET ;

pair_liter: NULL ;
