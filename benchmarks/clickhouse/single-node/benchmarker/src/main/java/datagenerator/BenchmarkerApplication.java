package datagenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Random;

@SpringBootApplication
public class BenchmarkerApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Random random = new Random(0);

    @Value("${uniqueUsers:1000}")
    private Integer uniqueUsers;
    @Value("${eventsPerUser:5000}")
    private Integer eventsPerUser;

    public static void main(String[] args) {
        var application = new SpringApplication(BenchmarkerApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    private void runQuery() {
//        var param = new HashMap<String, Object>();
//        param.put("id", random.nextInt(100));
        jdbcTemplate.execute("select * from user_event where user_id = " + random.nextInt(100) + " order by event_time desc limit 1000");
    }

    @Override public void run(String... args) throws Exception {
//        double min = 0;
//        double max = 0;
//        double total = 0;
//
//        int nQueries = 100;
//
//        for (int i = 0; i < nQueries; ++i) {
//            long start = System.currentTimeMillis();
//            runQuery();
//            long end = System.currentTimeMillis();
//            total += (end - start);
//            min = Double.min(min, end - start);
//            max = Double.max(max, end - start);
//        }
//
//        System.out.println(min + " " + max + " " + total / nQueries);

//        int queriesToRun = 1000;
    }

//    private static class UserEvent {
//        private Long userId;
//        private Long videoId;
//        private Long eventTime;
//        private Long eventType;
//
//        public UserEvent(Long userId, Long videoId, Long eventTime, Long eventType) {
//            this.userId = userId;
//            this.videoId = videoId;
//            this.eventTime = eventTime;
//            this.eventType = eventType;
//        }
//
//        public Long getUserId() {
//            return userId;
//        }
//
//        public Long getVideoId() {
//            return videoId;
//        }
//
//        public Long getEventTime() {
//            return eventTime;
//        }
//
//        public Long getEventType() {
//            return eventType;
//        }
//
//        @Override public String toString() {
//            return "UserEvent{" +
//                    "userId=" + userId +
//                    ", videoId=" + videoId +
//                    ", eventTime=" + eventTime +
//                    ", eventType=" + eventType +
//                    '}';
//        }
//    }
}
