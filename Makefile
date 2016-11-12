# Sample Makefile for the WACC Compiler lab: edit this to build your own compiler
# Locations

ANTLR_DIR	:= antlr

# Tools

ANTLR	:= antlrBuild

# the make rules

all: rules

rules:
	cd $(ANTLR_DIR) && ./$(ANTLR)
	sbt compile

clean:
	sbt clean

.PHONY: all rules clean

#mad
