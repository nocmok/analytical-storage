#!/bin/bash
yes "POST||localhost||test||select * from kion.user_event where user_id = 1 order by event_time" | head -n 10000 | python3 $(dirname $0)/make_ammo.py > $(dirname $0)/ammofile.txt