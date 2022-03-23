#!/bin/bash
source env.bash
docker-compose -f docker-compose.yml down
rm -r ./data
