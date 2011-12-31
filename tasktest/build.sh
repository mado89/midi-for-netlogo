#!/bin/bash

javac -classpath ../../netlogo-5.0RC4/NetLogo.jar -d bin at/*.java

if [ $? -eq 0 ]
then
	echo "Compiled"
	jar cvfm tasktest.jar manifest.txt -C bin .
	mv tasktest.jar tasktest
else
	echo "Compile failed"
fi
	


