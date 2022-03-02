#!/bin/bash
docker-compose -f $(dirname $0)/zookeeper/docker-compose-macos.yml up -d
docker-compose -f $(dirname $0)/kafka/docker-compose-macos.yml up -d