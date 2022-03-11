#!/bin/bash
yes "POST||192.168.88.129||test||select * from kion.test_table where value = 2 limit 5000" | head -n 1 | python3 $(dirname $0)/make_ammo.py > $(dirname $0)/loadtest/ammofile.txt