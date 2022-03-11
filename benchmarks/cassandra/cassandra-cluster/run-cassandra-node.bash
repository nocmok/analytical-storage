#!/bin/bash

if [[ -z "$CASSANDRA_NODE" ]]; then
	echo 'CASSANDRA_NODE variable not specified.'
	echo 'Please export CASSANDRA_NODE variable first, like so:' 
	echo 'export CASSANDRA_NODE=<number from 0 to 9>'
	exit 1
fi

## docker related variables
# port to expose outside the docker container for client connections
export CASSANDRA_EXTERNAL_CLIENT_PORT="904${CASSANDRA_NODE}"
# port to expose outside the docker container for intracluster communication
export CASSANDRA_EXTERNAL_GOSSIP_PORT="905${CASSANDRA_NODE}"
# docker container name for cassandra node
export CASSANDRA_CONTAINER_NAME="cassandra-node-${CASSANDRA_NODE}"
# docker bridge network name
export CASSANDRA_INTERNAL_NETWORK_NAME="cassandra-network-${CASSANDRA_NODE}"

## cluster related variables
export CASSANDRA_BROADCAST_ADDRESS="192.168.0.101"
export CASSANDRA_SEEDS="192.168.0.101,"
export CASSANDRA_ENDPOINT_SNITCH="GossipingPropertyFileSnitch"
export CASSANDRA_DC="vla"
export CASSANDRA_RACK="no_rack"
export CLUSTER_NAME="user_events"

## stargate related variables
export STARGATE_REST_API_PORT="906${CASSANDRA_NODE}"
export STARGATE_AUTH_PORT="907${CASSANDRA_NODE}"
export STARGATE_CONTAINER_NAME="stargate-cassandra-${CASSANDRA_NODE}"

docker compose -f $(dirname $0)/docker-compose.macos.yml up -d