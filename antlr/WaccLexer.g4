lexer grammar WaccLexer;

BEGIN : 'begin' ;
END : 'end' ;
IS : 'is' ;
SKIP : 'skip' ;
EQUALS : '=' ;
READ : 'read' ;
FREE : 'free' ;
RETURN : 'return' ;
EXIT : 'exit' ;
PRINT : 'print' ;
PRINTLN :  'println' ;
IF : 'if' ;
THEN : 'then' ;
ELSE : 'else' ;
FI : 'fi' ;
WHILE : 'while' ;
DO : 'do' ;
DONE : 'done' ;
NULL : 'null' ;

LPAREN : '(' ;
RPAREN : ')' ;
LBRACKET : '[' ;
RBRACKET : ']' ;
COMMA : ',' ;
SEMICOLON : ';' ;

EXCLAMATION : '!' ;
MINUS : '-' ;
LENGTH : 'len' ;
ORD : 'ord' ;
CHR : 'chr' ;

MULTIPLY : '*' ;
DIVIDE : '/' ;
MOD : '%' ;
PLUS : '+' ;
GREATER_THAN : '>' ;
GREATER_EQUAL : '>=' ;
LESS_THAN : '<' ;
LESS_EQUAL : '<=' ;
DOUBLE_EQUAL : '==' ;
NOT_EQUAL : '!=' ;
LOGICAL_AND : '&&' ;
LOGICAL_OR : '||' ;

UNDERSCORE : '_' ;

TRUE_LITERAL : 'true' ;
FALSE_LITERAL : 'false' ;

SINGLE_QUOTE : '\'' ;
DOUBLE_QUOTE : '"' ;
HASH : '#' ;

NEWPAIR : 'newpair' ;
CALL : 'call' ;

FIRST : 'fst' ;
SECOND : 'snd' ;

INT_TYPE : 'int' ;
BOOL_TYPE : 'bool' ;
CHAR_TYPE : 'char' ;
STRING_TYPE : 'string' ;
PAIR_TYPE : 'pair' ;


ID : [a-zA-Z]+ ;
WS : [ \t\r\n]+ -> skip;
