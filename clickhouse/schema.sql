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

create table if not exists kion.user_event_kafka
(
    user_id Int64, 
    video_id Int64, 
    event_time Int64, 
    event_type Int64
) ENGINE = Kafka()
SETTINGS
    kafka_broker_list = '${KAFKA_BOOTSTRAP_SERVERS}',
    kafka_topic_list = 'ru.analytical.storage.event',
    kafka_group_name = 'ru.analytical.storage.event.clickhouse',
    kafka_format = 'JSONEachRow',
    kafka_max_block_size = 100000,
    kafka_num_consumers = 1,
    kafka_skip_broken_messages = 100000
;

create materialized view if not exists kion.user_event_kafka_view to kion.user_event
    as select user_id, video_id, event_time, event_type
    from kion.user_event_kafka
;