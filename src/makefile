JFLAGS = -g
JC = javac
JV = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	*.java\
	affichage/*.java\
	tokemisation/*.java\
	lemmatiseur/*.java\
	synonymes/*.java\
	listelemm/*.java\
	ASP/*.java\
	utils/*.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	$(RM) tokemisation/*.class
	$(RM) affichage/*.class
	$(RM) lemmatiseur/*.class
	$(RM) synonymes/*.class
	$(RM) listelemm/*.class
	$(RM) ASP/*.class
	$(RM) utils/*.class

tokem:
	$(JV) TestTokem

main:
	$(JV) Main
