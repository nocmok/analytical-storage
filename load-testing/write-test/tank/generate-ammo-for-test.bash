#!/bin/bash
echo 'POST||/api/user-event/save||test||{"user_id":1,"video_id":1,"event_type":1,"event_time":1}' | python3 $(dirname $0)/make_ammo.py >> $(dirname $0)/ammofile.txt