#!/bin/bash

yes y | bash $(dirname $0)/kafka/kafka-connect/stop-kafka-connect.bash
yes y | bash $(dirname $0)/ReceiverService/stop-receiver-service.bash
yes y | bash $(dirname $0)/prometheus/stop-prometheus.bash
yes y | bash $(dirname $0)/kafka/kafka/stop-kafka.bash
yes y | bash $(dirname $0)/cassandra/cassandra/stop-cassandra.bash
yes y | bash $(dirname $0)/zookeeper/stop-zookeeper.bash