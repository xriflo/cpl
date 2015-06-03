#!/bin/bash

TESTS="_tests"
RUNTIME="_runtime"
OUTPUT="out"

function fail {
		printf "FAIL (%s)\n" "$1"
}

function pass {
	printf "PASS\n"
}

run()
{
  java -jar LCPLRun.jar --run $1
}

translate()
{
  OUTFILE=$OUTPUT/${2%c}out
  ERRORFILE=$OUTPUT/${2%c}err
  EXEC=$OUTPUT/${2%.c}
  INFILE="$TESTS/advanced/io.in"
  
  
  java -jar LCPLRun.jar --translate $1 $OUTPUT/$2
 
  gcc $OUTPUT/$2 $RUNTIME/lcpl_runtime.c -I _runtime -o $EXEC

  if [ $1 = "$TESTS/advanced/io.run" ]
  then
    ./$EXEC < $INFILE > $OUTFILE 2> $ERRORFILE
   else
     ./$EXEC > $OUTFILE 2> $ERRORFILE
  fi
  

 if [ -z "$( diff -q -b $OUTFILE ${1%run}ref )" ]; then
	pass
 else
	fail "Different program output. First lines of diff below"
	echo "-----"
	diff $OUTFILE ${1%run}ref | head -n 30
	echo "-- Error log: --"
	cat $ERRORFILE
	echo "-----"
	echo
fi
}

test()
{
  echo  === $1 ===
  
  FNAME=`basename $1`
  
  echo  --- execution test ---
  
  if [ $1 = "$TESTS/advanced/io.run" ]
  then
	run $1 > $OUTPUT/${FNAME%run}out 2> $OUTPUT/${FNAME%run}err << END
James
7
END
  else
	run $1 > $OUTPUT/${FNAME%run}out 2> $OUTPUT/${FNAME%run}err
  fi
  
 if [ -z "$( diff -q -b $OUTPUT/${FNAME%run}out ${1%run}ref )" ]; then
	pass
 else
	fail "Different program output. First lines of diff below"
	echo "-----"
	diff $OUTPUT/${FNAME%run}out ${1%run}ref | head -n 30
	echo "-- Error log: --"
	cat $OUTPUT/${FNAME%run}err
	echo "-----"
	echo
fi

  echo  --- translation test ---
  
  CNAME=${FNAME%run}c
  
  translate $1 $CNAME 
  
}

mkdir out

echo "******************************"
echo "**** Running simple tests ****"
echo "******************************"
echo 

for l in $TESTS/*.run
do
	test $l
done
for l in $TESTS/simple/*.run
do
	test $l
done

echo
echo


echo "********************************"
echo "**** Running advanced tests ****"
echo "********************************"
echo

for l in $TESTS/advanced/*.run
do
	test $l
done

echo
echo

echo "*******************************"
echo "**** Running complex tests ****"
echo "*******************************"
echo
for l in $TESTS/complex/*.run
do
	test $l
done

rm -f __stdout
rm -f __stderr
