#!/bin/bash

docker-compose -f $(dirname $0)/zookeeper/docker-compose.local.yml up -d
docker-compose -f $(dirname $0)/kafka/kafka/docker-compose.local.yml up -d
docker-compose -f $(dirname $0)/cassandra/cassandra/docker-compose.local.yml up -d
docker-compose -f $(dirname $0)/clickhouse/docker-compose.local.yml up -d
docker-compose -f $(dirname $0)/kafka/kafka-connect/docker-compose.local.yml up -d
docker-compose -f $(dirname $0)/receiver-service/docker-compose.local.yml up -d
docker-compose -f $(dirname $0)/prometheus/docker-compose.local.yml up -d