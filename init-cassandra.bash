#!/bin/bash

cqlsh localhost 19042 -f $(dirname $0)/cassandra/cassandra/schema.cql