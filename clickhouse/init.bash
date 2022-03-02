#!/bin/bash

if [[ -z $CLICKHOUSE_USER  ]]; then
	CLICKHOUSE_USER='default'
fi

if [[ -z $CLICKHOUSE_PASSWORD ]]; then
	clickhouse client -h localhost --port 19001 -u $CLICKHOUSE_USER --password '' --queries-file $(dirname $0)/schema.sql
else
	clickhouse client -h localhost --port 19001 -u $CLICKHOUSE_USER --password $CLICKHOUSE_PASSWORD --queries-file $(dirname $0)/schema.sql
fi