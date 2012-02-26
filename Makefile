ifeq ($(origin JAVA_HOME), undefined)
  JAVA_HOME=/usr
endif

#ifeq ($(origin NETLOGO), undefined)
#  NETLOGO=../..
#endif

#echo $NETLOGO

JAVAC=$(JAVA_HOME)/bin/javac
SRCS=$(wildcard src/at/univie/csd/*.java src/at/univie/csd/command/*.java src/at/univie/csd/midi/*.java)

all: midi.zip midi.full.zip

midi.jar: $(SRCS) manifest.mf Makefile
	mkdir -p bin
	$(JAVAC) -g -deprecation -Xlint:all -Xlint:-serial -Xlint:-path -encoding us-ascii -source 1.5 -target 1.5 -classpath /media/data/Uni/DA/netlogo-5.0/NetLogo.jar -d bin $(SRCS)
	jar cmf manifest.mf midi.jar -C bin .

midi.zip: midi.jar
	rm -rf midi
	mkdir midi
	cp -rp midi.jar Makefile src manifest.mf midi
	zip -rv midi.zip midi
	rm -rf midi
	
midi.full.zip: midi.jar midi.zip
	cp midi.zip midi.full.zip
	mkdir midi
	mkdir midi/doc
	mkdir midi/ExampleModels
	cp doc/midi.html midi/doc
	cp ../germanDoc/Arbeit.pdf midi/doc/GermanDescription.pdf
	cp ../description.pdf midi/doc/description.pdf
	cp ../ExampleModels/v5/*.nlogo midi/ExampleModels
	zip midi.full.zip midi/doc/* midi/ExampleModels/*

