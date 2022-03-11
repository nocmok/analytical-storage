create keyspace if not exists kion with replication = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

create table if not exists kion.user_event (user_id bigint, video_id bigint, event_type bigint, event_time bigint, primary key (user_id, event_time));

