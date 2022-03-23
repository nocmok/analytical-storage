#!/bin/bash

export config_file=$(dirname $0)/cassandra-sink-connector-config.json
source $(dirname $0)/env.bash
curl -s -X POST -H 'Content-Type: application/json' -d "$(cat $config_file | envsubst)" http://localhost:8083/connectors