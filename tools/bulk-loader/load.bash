#!/bin/bash

data_loaded=400000000
data_to_load=800000000

rm "$(dirname "$0")/progress.txt"

while (( data_loaded < data_to_load )); do
  echo "written 0 rows (0 ms)" >> "$(dirname "$0")/progress.txt"
  echo "start loading $(( data_to_load - data_loaded )) rows ..."
  java -jar "$(dirname "$0")"/bulk-loader.jar $(( data_to_load - data_loaded )) 2>/dev/null 1>> "$(dirname "$0")/progress.txt" \
    && break
  echo "failed. data loaded on this attempt = $(tail -n 1 "$(dirname "$0")/progress.txt" | cut -f 2 -w). will retry"
  (( data_loaded += $(tail -n 1 "$(dirname "$0")/progress.txt" | cut -f 2 -w) ))
  echo "total data loaded $data_loaded"
done

echo "done!"