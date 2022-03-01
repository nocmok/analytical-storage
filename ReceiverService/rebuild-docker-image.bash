mvn clean install

docker build -t dachertanov/receiver-service:latest .

docker push dachertanov/receiver-service:latest