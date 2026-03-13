package org.redflag.dto.organization.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Introspected
@Serdeable
public class CreateOrganizationRequest {

    @JsonProperty("name")
    @Schema(description = "Название организации", requiredMode = Schema.RequiredMode.REQUIRED, example = "ООО Хихи-хаха")
    private final String name;
}
