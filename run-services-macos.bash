#!/bin/bash
docker-compose -f $(dirname $0)/zookeeper/docker-compose-macos.yml up -d \
&& docker-compose -f $(dirname $0)/kafka/docker-compose-macos.yml up -d \
&& docker-compose -f $(dirname $0)/clickhouse/docker-compose-macos.yml up -d \
&& docker-compose -f $(dirname $0)/ReceiverService/docker-compose.receiver.yml up -d \
&& docker-compose -f $(dirname $0)/prometheus/docker-compose-macos.yml up -d \
&& bash $(dirname $0)/clickhouse/init.bash