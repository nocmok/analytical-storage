package data_generator;

import com.datastax.oss.driver.api.core.CqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletionStage;

@SpringBootApplication
public class DataGeneratorApplication  {

    private static final Logger log = LoggerFactory.getLogger(DataGeneratorApplication.class);
    @Autowired
    private CqlSession session;
    private Integer batchSize = 8000;
    private Integer batchesToInsert = 1000;
    private Integer nThreads = 2000;
    private Random random = new Random();

    public static void main(String[] args) {
        SpringApplication.run(DataGeneratorApplication.class, args);
    }

    private List<UserEvent> getBatch(int batchSize) {
        var batch = new ArrayList<UserEvent>();
        for (int i = 0; i < batchSize; ++i) {
            batch.add(new UserEvent((long) (random.nextInt(1000) + 1),
                    (long) (random.nextInt(100000) + 1),
                    (long) (random.nextInt(10) + 1),
                    (long) (random.nextInt(Integer.MAX_VALUE))));
        }
        return batch;
    }

    private void batchInsert(List<UserEvent> batch) {
        var preparedStatement = session.prepare("insert into kion.user_event (user_id, video_id, event_type, event_time) values(?,?,?,?)");
        var jobs = new ArrayList<CompletionStage<?>>();
        for (var event : batch) {
            jobs.add(session.executeAsync(preparedStatement.bind(
                    event.getUserId(),
                    event.getVideoId(),
                    event.getEventType(),
                    event.getEventTime()
            )));

            if (jobs.size() >= nThreads) {
                for (var future : jobs) {
                    try {
                        future.toCompletableFuture().join();
                    } catch (Exception ignore) {
                        log.warn("async insert was interrupted");
                    }
                }
                jobs.clear();
            }
        }
    }

    private void inflateUserEventTable() {
        for (int i = 0; i < batchesToInsert; ++i) {
            var batch = getBatch(batchSize);
            long start = System.currentTimeMillis();
            batchInsert(batch);
            long end = System.currentTimeMillis();
            System.out.println("written " + ((i + 1) * batchSize) + " rows " + " (" + (end - start) + " ms)");
        }
    }

    public void run(String... args) throws Exception {
        try {
            inflateUserEventTable();
        } finally {
            session.close();
        }
    }

    private static class UserEvent {
        private Long userId;
        private Long videoId;
        private Long eventType;
        private Long eventTime;

        public UserEvent(Long userId, Long videoId, Long eventType, Long eventTime) {
            this.userId = userId;
            this.videoId = videoId;
            this.eventType = eventType;
            this.eventTime = eventTime;
        }

        public Long getUserId() {
            return userId;
        }

        public Long getVideoId() {
            return videoId;
        }

        public Long getEventType() {
            return eventType;
        }

        public Long getEventTime() {
            return eventTime;
        }
    }
}
