#!/bin/bash
yes "POST||192.168.88.129||test||select * from kion.user_event where user_id = 1 order by event_time desc limit 1000" | head -n 1 | python3 $(dirname $0)/make_ammo.py > $(dirname $0)/loadtest/ammofile.txt