JFLAGS = -g
JC = javac
JV = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	*.java\
	./tokemisation/*.java\
	synonymes/*.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	$(RM) ./tokemisation/*.class
	$(RM) synonymes/*.class

tokem: 
	$(JV) TestTokem

syn:
	$(JV) TestSynonymes
