mvn clean install

docker build --platform linux/amd64 -t dachertanov/receiver-service:latest .

docker push dachertanov/receiver-service:latest