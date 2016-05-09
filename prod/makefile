JFLAGS = -g
JC = javac
JV = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	*.java\
	./affichage/*.java\
	./lemmatiseur/*.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	$(RM) ./tokemisation/*.class

tokem: 
	$(JV) TestTokem 

main: 
	$(JV) Main 