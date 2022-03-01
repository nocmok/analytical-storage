package ru.analytical.storage.receiver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.analytical.storage.receiver.dto.UserEvent;
import ru.analytical.storage.receiver.kafka.producer.UserEventsProducer;

@Tag(name = "Активность пользователя", description = "Сохраняет историю активности пользователя")
@RestController
@RequestMapping("/api/user-event")
@AllArgsConstructor
public class UserEventController {
    private final UserEventsProducer userEventsProducer;

    @PostMapping("/save")
    @Operation(summary = "Сохраняет событие пользователя")
    public ResponseEntity<String> saveUserEvent(@RequestBody UserEvent userEvent) {
        userEventsProducer.sendUserEvent(String.valueOf(userEvent.getUserId()), userEvent);
        return ResponseEntity.ok().build();
    }
}
