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

JFLAGS	:= -sourcepath $(SOURCE_DIR) -d $(OUTPUT_DIR) -cp lib/antlr-4.4-complete.jar

# the make rules

all: rules

rules:
	cd $(ANTLR_DIR) && ./$(ANTLR)
	bin/sbt compile

clean:
	bin/sbt clean

.PHONY: all rules clean
