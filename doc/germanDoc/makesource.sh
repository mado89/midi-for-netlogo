#!/bin/bash

#rm sourcen.tex
#touch sourcen.tex

#echo "\section{Source Code}" >>> sourcen.tex

for FINA in `find ../midi/src/at/univie/csd/ -name '*.java'`
do
	echo "\subsection{" `basename ${FINA}` "}"
	#echo "\lstinputlisting[language=Java,name=code1,caption={Source-Code},label=code]{" ${FINA} "}"
	echo "\lstinputlisting[language=Java]{" ${FINA} "}"
done

