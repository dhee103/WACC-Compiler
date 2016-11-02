parser grammar WaccParser;

options {
  tokenVocab=WaccLexer;
}

// EOF indicates that the program must consume to the end of the input.
prog: BEGIN stat END EOF ;

stat: SKIP ;
