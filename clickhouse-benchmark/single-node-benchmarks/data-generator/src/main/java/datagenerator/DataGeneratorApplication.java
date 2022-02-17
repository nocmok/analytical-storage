package datagenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class DataGeneratorApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Random random = new Random(0);

    @Value("${uniqueUsers:1000}")
    private Integer uniqueUsers;
    @Value("${eventsPerUser:5000}")
    private Integer eventsPerUser;

    public static void main(String[] args) {
        SpringApplication.run(DataGeneratorApplication.class, args);
    }

    private UserEvent randomUserEvent(Long userId) {
        return new UserEvent(userId, random.nextLong(), Instant.now().toEpochMilli(), random.nextLong() % 5 + 4);
    }

    private void insertBatch(List<UserEvent> batch) {
        jdbcTemplate.batchUpdate(" insert into user_event(user_id,video_id,event_time,event_type) values(?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override public void setValues(PreparedStatement ps, int i) throws SQLException {
                        var event = batch.get(i);
                        ps.setLong(1, event.getUserId());
                        ps.setLong(2, event.getVideoId());
                        ps.setLong(3, event.getEventTime());
                        ps.setLong(4, event.getEventType());
                    }

                    @Override public int getBatchSize() {
                        return batch.size();
                    }
                });
    }

    @Override public void run(String... args) throws Exception {
        for (long userId = 0; userId < uniqueUsers; ++userId) {
            long userIdFinal = userId;
            var batch = IntStream.range(0, eventsPerUser)
                    .mapToObj(i -> randomUserEvent(userIdFinal))
                    .collect(Collectors.toCollection(ArrayList::new));
            insertBatch(batch);
            System.out.println("data inserted " + (userId + 1) * eventsPerUser);
        }
    }

    private static class UserEvent {
        private Long userId;
        private Long videoId;
        private Long eventTime;
        private Long eventType;

        public UserEvent(Long userId, Long videoId, Long eventTime, Long eventType) {
            this.userId = userId;
            this.videoId = videoId;
            this.eventTime = eventTime;
            this.eventType = eventType;
        }

        public Long getUserId() {
            return userId;
        }

        public Long getVideoId() {
            return videoId;
        }

        public Long getEventTime() {
            return eventTime;
        }

        public Long getEventType() {
            return eventType;
        }

        @Override public String toString() {
            return "UserEvent{" +
                    "userId=" + userId +
                    ", videoId=" + videoId +
                    ", eventTime=" + eventTime +
                    ", eventType=" + eventType +
                    '}';
        }
    }
}
