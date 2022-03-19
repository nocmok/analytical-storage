#!/bin/bash

data_loaded=0
data_to_load=800000000

while (( data_loaded < data_to_load )); do

  java -jar "$(dirname "$0")"/bulk-loader.jar > progress.txt && break

  echo "failed. data loaded = $(tail -n 1 | cur -f 2 -w). will retry"

  (( data_loaded += $(tail -n 1 | cur -f 2 -w) ))

done