#!/bin/bash

source $(dirname $0)/env.bash
cat $(dirname $0)/prometheus.yml | envsubst > $(dirname $0)/.prometheus.yml
docker-compose -f $(dirname $0)/docker-compose.yml up -d