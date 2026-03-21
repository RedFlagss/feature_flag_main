package org.redflag.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

import java.util.UUID;

@Builder
@Serdeable
public record SessionResponseDto(

        String login,
        UUID uuidDepartament
) {

}