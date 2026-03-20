package org.redflag.dto;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record SdkClientResponse(

        Long id,

        String login,

        @Nullable
        String password
) {}