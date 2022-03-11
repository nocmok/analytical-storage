#!/bin/bash
yes 'GET||/v2/keyspaces/kion/user_event?where={"user_id":{"$eq":1}}||test||' | head -n 1 | python3 $(dirname $0)/make_ammo.py > $(dirname $0)/ammofile.txt