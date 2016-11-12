ANTLR_DIR	:= antlr

# Tools

ANTLR	:= antlrBuild

# the make rules

all: rules

rules:
	cd $(ANTLR_DIR) && ./$(ANTLR)
	sbt package
	sbt compile

clean:
	# $(RM) rules $(OUTPUT_DIR)
	sbt clean


.PHONY: all rules clean
