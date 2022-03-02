create database if not exists kion;

create table if not exists kion.user_event 
(
    user_id Int64, 
    video_id Int64, 
    event_time Int64, 
    event_type Int64
) 
ENGINE = MergeTree() 
ORDER BY (user_id, event_time, event_type, video_id);