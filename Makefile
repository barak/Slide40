all: slide40.jar # works okay, invoke as: java -jar slide40.jar
# all: slide40 # file selection fails, invoke as: ./slide40

GCJ=gcj
GCJFLAGS=-Wall -O2 -g
jsrc=Alphabet.java Glyph.java Presentation.java SlideForty.java Terminal.java

slide40: $(jsrc)
	$(GCJ) $(GCJFLAGS) --main=SlideForty -o $@ $^

JAVAC=javac

jclass=Alphabet.class Glyph.class Presentation.class			\
 SlideForty$$1.class SlideForty.class SlideForty$$FileMenu.class	\
 SlideForty$$FileMenu$$TextFilter.class					\
 SlideForty$$MyComponentAdapter.class SlideForty$$MyKeyListener.class	\
 Terminal.class

$(jclass): $(jsrc)
	$(JAVAC) SlideForty.java

slide40.jar: $(jclass)
	jar cfe $@ SlideForty $(subst $$,\$$,$^)

clean:
	-rm slide40
	-rm slide40.jar
	-rm $(subst $$,\$$,$(jclass))

.PHONY: all clean
