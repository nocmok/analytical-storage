#!/bin/bash

curl -X DELETE http://localhost:18083/connectors/cassandra_sink_connector

export KAFKA_CONNECT_BOOTSTRAP_SERVERS=kafka:9092
export CASSANDRA_HOSTS=cassandra
export CASSANDRA_CLIENT_PORT=9042
export WRITE_CONSISTENCY_LEVEL=ONE

export connector_config=$(dirname $0)/kafka/kafka-connect/cassandra-sink-connector-config.json
curl -s -X POST -H 'Content-Type: application/json' -d $(cat "$connector_config" | envsubst)" http://localhost:18083/connectors