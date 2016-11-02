lexer grammar WaccLexer;

BEGIN : 'begin' ;
END : 'end' ;
IS : 'is' ;
COMMA : ',' ;
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
SEMICOLON : ';' ;

FIRST : 'fst' ;
SECOND : 'snd' ;

INT : 'int' ;
BOOL : 'bool' ;
CHAR : 'char' ;
STRING : 'string' ;
PAIR : 'pair' ;


ID : [a-zA-Z]+ ;
INT  : [0-9]+ ;
WS : [ \t\r\n]+ -> skip;
