#!/bin/bash
yes "GET||/||random-read||" | head -n 1 | python3 $(dirname $0)/make_ammo.py > $(dirname $0)/ammofile.txt