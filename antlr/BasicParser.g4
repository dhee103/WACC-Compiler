parser grammar BasicParser;

options {
  tokenVocab=BasicLexer;
}

// EOF indicates that the program must consume to the end of the input.
prog: BEGIN (func)* stat END EOF ;

func: type ident LPAREN (param_list)? RPAREN IS stat END ;

param_list: param (COMMA param)* ;

param: type ident ;

stat: SKIP_
    | type ident EQUALS assign_rhs
    | assign_lhs EQUALS assign_rhs
    | FREE expr
    | RETURN expr
    | EXIT expr
    | PRINT expr
    | PRINTLN expr
    | IF expr THEN stat ELSE stat FI
    | WHILE expr DO stat DONE
    | BEGIN stat END
    | stat SEMICOLON stat
    ;

assign_lhs: ident
          | array_elem
          | pair_elem
          ;

assign_rhs: expr
          | array_liter
          | NEWPAIR LPAREN expr COMMA expr RPAREN
          | pair_elem
          | CALL ident LPAREN (arg_list)? RPAREN
          ;

arg_list: expr (COMMA expr)* ;

pair_elem: FIRST expr
         | SECOND expr
         ;

type: base_type
    | type LBRACKET RBRACKET
    | pair_type
    ;

base_type: INT_TYPE
         | BOOL_TYPE
         | CHAR_TYPE
         | STRING_TYPE
         ;

pair_type: PAIR_TYPE LPAREN pair_elem_type COMMA pair_elem_type RPAREN ;

pair_elem_type: base_type
              | type LBRACKET RBRACKET
              | PAIR_TYPE
              ;

expr: int_liter
    | bool_liter
    | char_liter
    | str_liter
    | pair_liter
    | ident
    | array_elem
    | unary_oper expr
    | expr binary_oper expr
    | LPAREN expr RPAREN
    ;

unary_oper: EXCLAMATION
          | MINUS
          | LENGTH
          | ORD
          | CHR
          ;

binary_oper: MULTIPLY
           | DIVIDE
           | MOD
           | PLUS
           | GREATER_THAN
           | GREATER_EQUAL
           | LESS_THAN
           | LESS_EQUAL
           | DOUBLE_EQUAL
           | NOT_EQUAL
           | LOGICAL_AND
           | LOGICAL_OR
           ;

ident: ID ;

array_elem: ident (LBRACKET expr RBRACKET)+ ;

int_liter: INT_LITERAL ;

bool_liter: TRUE_LITERAL
          | FALSE_LITERAL
          ;

char_liter: CHAR_LITER ;

str_liter: STR_LITER ;

array_liter: LBRACKET ((expr) (COMMA expr)*)? RBRACKET ;

pair_liter: NULL ;


