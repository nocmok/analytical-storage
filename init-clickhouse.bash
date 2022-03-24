#!/bin/bash

export KAFKA_BOOTSTRAP_SERVERS=kafka:9092
cat $(dirname $0)/clickhouse/schema.sql | envsubst > $(dirname $0)/.schema.sql
clickhouse client -h localhost --port 19000 -u default --queries-file $(dirname $0)/.schema.sql
rm $(dirname $0)/.schema.sql