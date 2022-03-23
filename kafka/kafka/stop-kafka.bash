#!/bin/bash
source $(dirname $0)/env.bash
docker-compose -f $(dirname $0)/docker-compose.yml down