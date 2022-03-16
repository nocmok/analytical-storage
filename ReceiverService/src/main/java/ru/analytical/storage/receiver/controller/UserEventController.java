package ru.analytical.storage.receiver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.analytical.storage.receiver.dto.UserEvent;
import ru.analytical.storage.receiver.kafka.producer.UserEventsProducer;

import java.util.Random;

@Slf4j
@Tag(name = "Активность пользователя", description = "Сохраняет историю активности пользователя")
@RestController
@RequestMapping("/api/user-event")
public class UserEventController {
    private final UserEventsProducer userEventsProducer;
    private final Random rand;

    public UserEventController(UserEventsProducer userEventsProducer) {
        this.userEventsProducer = userEventsProducer;
        this.rand = new Random();
    }

    @PostMapping("/save")
    @Operation(summary = "Сохраняет событие пользователя")
    public ResponseEntity<String> saveUserEvent(@RequestBody UserEvent userEvent) {
        changeUserEventToRandomValue(userEvent);
        log.info("Random UserEvent: {}", userEvent);

        userEventsProducer.sendUserEvent(String.valueOf(userEvent.getUserId()), userEvent);
        return ResponseEntity.ok().build();
    }

    private void changeUserEventToRandomValue(UserEvent userEvent) {
        userEvent.setUserId((long) rand.nextInt(100000));
        userEvent.setVideoId((long) rand.nextInt(100000));
        userEvent.setEventType((long) rand.nextInt(100000));
        userEvent.setEventTime((long) rand.nextInt(100000));
    }
}
