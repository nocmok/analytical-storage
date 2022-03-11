#!/bin/bash
yes y | {
	docker-compose -f $(dirname $0)/zookeeper/docker-compose-macos.yml rm -sv \
	&& docker-compose -f $(dirname $0)/kafka/docker-compose-macos.yml rm -sv \
	&& docker-compose -f $(dirname $0)/clickhouse/docker-compose-macos.yml rm -sv \
	&& docker-compose -f $(dirname $0)/ReceiverService/docker-compose.receiver.yml rm -sv \
	&& docker-compose -f $(dirname $0)/prometheus/docker-compose-macos.yml rm -sv
}