echo "rows min max avg" >> data.csv
for (( i=0;i<100;++i )); do
	benchmark=$(java -jar benchmarker.jar)
	echo "$((i * 5000)) $benchmark" >> data.csv
	java -jar inflater.jar > /dev/null
	echo "completed $i"
done