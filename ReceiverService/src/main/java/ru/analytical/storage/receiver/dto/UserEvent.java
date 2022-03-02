package ru.analytical.storage.receiver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "DTO для получения событий пользователя")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class UserEvent {
    @Schema(description = "id видео", example = "1")
    @NotNull
    private Long videoId;

    @Schema(description = "id пользователя", example = "1")
    @NotNull
    private Long userId;

    @Schema(description = "Тип события", example = "1")
    @NotNull
    private Long eventType;

    @Schema(description = "Время события в мс c какой-то даты", example = "1")
    private Long eventTime;
}
