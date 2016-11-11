# Sample Makefile for the WACC Compiler lab: edit this to build your own compiler
# Locations

ANTLR_DIR	:= antlr
SOURCE_DIR	:= src
OUTPUT_DIR	:= bin
SBT ?= sbt

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
	$(FIND) $(SOURCE_DIR) -name '*.java' > $@
	$(MKDIR) $(OUTPUT_DIR)
	$(JAVAC) $(JFLAGS) @$@
	$(RM) rules
	$(SBT) package
	# sbt compile

clean:
	sbt clean

.PHONY: all rules clean
