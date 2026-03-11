package org.redflag.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Introspected
@Serdeable
public class ErrorDTO {
    @Schema(description = "Уникальный идентификатор ошибки", requiredMode = Schema.RequiredMode.REQUIRED, example = "00-0000")
    private String code;
    @Schema(description = "Категория ошибки", requiredMode = Schema.RequiredMode.REQUIRED, example = "Неожиданная ошибка выполнения")
    private String errorType;
    @Schema(description = "Сообщение ошибки", requiredMode = Schema.RequiredMode.REQUIRED, example = "Index 1 out of bounds for length 0")
    private String message;

}
