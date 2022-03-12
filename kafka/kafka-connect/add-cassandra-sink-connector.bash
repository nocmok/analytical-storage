#!/bin/bash

curl -s -X POST -H 'Content-Type: application/json' --data @cassandra-sink-connector-config.json http://localhost:8083/connectors