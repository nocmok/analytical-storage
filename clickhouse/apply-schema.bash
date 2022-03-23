#!/bin/bash

source $(dirname $0)/env.bash

if [[ -z $CLICKHOUSE_USER  ]]; then
	CLICKHOUSE_USER='default'
fi

cat $(dirname $0)/schema.sql | envsubst > $(dirname $0)/.schema.sql

if [[ -z $CLICKHOUSE_PASSWORD ]]; then
	clickhouse client -h localhost --port ${CLICKHOUSE_TCP_PORT} -u $CLICKHOUSE_USER --password '' --queries-file $(dirname $0)/.schema.sql
else
	clickhouse client -h localhost --port ${CLICKHOUSE_TCP_PORT} -u $CLICKHOUSE_USER --password $CLICKHOUSE_PASSWORD --queries-file $(dirname $0)/.schema.sql
fi