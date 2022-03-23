#!/bin/bash

export KAFKA_CLIENT_EXTERNAL_PORT=29092
export PROMETHEUS_JMX_EXTERNAL_PORT=9102
export KAFKA_ZOOKEEPER_HOST=host.docker.internal
export KAFKA_ZOOKEEPER_PORT=2181
export MY_PUBLIC_IP=host.docker.internal