# Sample Makefile for the WACC Compiler lab: edit this to build your own compiler
# Locations

ANTLR_DIR	:= antlr
SOURCE_DIR	:= src
OUTPUT_DIR	:= bin

# Tools

ANTLR	:= antlrBuild
FIND	:= find
RM	:= rm -rf
MKDIR	:= mkdir -p
JAVA	:= java
JAVAC	:= javac

JFLAGS	:= -sourcepath $(SOURCE_DIR) -d $(OUTPUT_DIR) -cp lib/antlr-4.5.3-complete.jar

# the make rules

all: rules

rules:
	cd $(ANTLR_DIR) && ./$(ANTLR)
	sbt compile

clean:
	sbt clean

run: 
	sbt "run-main Main #{ARGS}"

.PHONY: all rules clean
