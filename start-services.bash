#!/bin/bash

bash $(dirname $0)/zookeeper/start-zookeeper.bash
bash $(dirname $0)/kafka/kafka/start-kafka.bash
bash $(dirname $0)/cassandra/cassandra/start-cassandra.bash
bash $(dirname $0)/kafka/kafka-connect/start-kafka-connect.bash
bash $(dirname $0)/ReceiverService/start-receiver-service.bash
bash $(dirname $0)/prometheus/start-prometheus.bash