package http_adapter;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class Controller {

    @Autowired
    private CqlSession session;

    @Value("${http_adapter.rows_to_fetch}")
    private Integer rowsToFetch;

    private Random random = new Random();

    private List<UserEvent> mapRsToUserEventList(ResultSet rs) {
        var events = new ArrayList<UserEvent>();
        for (var row : rs) {
            events.add(new UserEvent(
                    row.getLong(0),
                    row.getLong(1),
                    row.getLong(2),
                    row.getLong(3)
            ));
        }
        return events;
    }

    @GetMapping
    public List<UserEvent> selectRequest() {
        var preparedStatement = session.prepare("select * from kion.user_event where user_id = ? limit " + rowsToFetch);
        return mapRsToUserEventList(session.execute(preparedStatement.bind((long) random.nextInt(1001) + 1)));
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
