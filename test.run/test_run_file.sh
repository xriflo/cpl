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
  
}

mkdir out

echo 
echo "Usage - ./test_run_file.sh *filename*"
echo 

test $1

rm -f __stdout
rm -f __stderr
