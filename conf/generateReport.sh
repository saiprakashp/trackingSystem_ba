#!/bin/bash

for i in $(seq 2018  $1)
do
echo "Started Generating report for year: [$i]"
java -cp Reports.jar:lib/*:. connectionTest.TestCon $i MONTHLY -1
java -cp Reports.jar:lib/*:. connectionTest.TestCon $i YEARLY -1
if [ $i == $(date +%Y) ]
 then
 break
fi

done
