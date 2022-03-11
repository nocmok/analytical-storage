package data_generator;

import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class DataGeneratorApplication implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;
    private Integer batchSize = 32000;
    private Integer batchesToInsert = 100;
    private Random random = new Random();

    public static void main(String[] args) {
        SpringApplication.run(DataGeneratorApplication.class, args);
    }

    private List<UserEvent> getBatchForUser(int batchSize) {
        var batch = new ArrayList<UserEvent>();
        for (int i = 0; i < batchSize; ++i) {
            batch.add(new UserEvent((long) (random.nextInt(1000) + 1),
                    (long) (random.nextInt(1000) + 1),
                    (long) (random.nextInt(10) + 1), System.currentTimeMillis()));
        }
        return batch;
    }

    private Document mapUserEventToDocument(UserEvent userEvent) {
        return new Document()
                .append("user_id", userEvent.getUserId())
                .append("video_id", userEvent.getVideoId())
                .append("event_type", userEvent.getEventType())
                .append("event_time", userEvent.getEventTime());
    }

    @Override public void run(String... args) throws Exception {
        inflateUserEventTable();
    }

    private void inflateUserEventTable() {
        var userEventCollection = mongoTemplate.getCollection("user_event");
        for (int i = 0; i < batchesToInsert; ++i) {
            var batch = getBatchForUser(batchSize);
            var bulkList = new ArrayList<WriteModel<Document>>();

            for (var userEvent : batch) {
                bulkList.add(new UpdateOneModel<>(
                        new Document("_id", userEvent.getUserId()),
                        new Document("$push", new Document("events", mapUserEventToDocument(userEvent))),
                        new UpdateOptions().upsert(true)
                ));
            }

            long start = System.currentTimeMillis();
            userEventCollection.bulkWrite(bulkList);
            long end = System.currentTimeMillis();

            System.out.println("written " + ((i + 1) * batchSize) + " rows " + " (" + (end - start) + " ms)");
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
