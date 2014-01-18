#
# Define compilador y flags
#

JFLAGS = -g
JC = javac
RM = rm -f

default: 
	$(JC) $(JFLAGS) *.java
#
# para remover los objetos compilados
#

clean: 
	$(RM) *.class