package org.redflag.dto.organization.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Introspected
@Serdeable
public class GetOrganizationsResponse {

    @JsonProperty("items")
    @Schema(description = "Массив организаций", requiredMode = Schema.RequiredMode.REQUIRED)
    private final List<OrganizationDTO> items;

    @JsonProperty("limit")
    @Schema(description = "Верхний лимит количества записей для текущего массива", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
    private final Integer limit;

    @JsonProperty("offset")
    @Schema(description = "Начальный номер записи от начала для текущего массива", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private final Integer offset;

    @JsonProperty("total")
    @Schema(description = "Фактическое количество записей", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private final Integer total;

    @Data
    @Introspected
    @Serdeable
    public static final class OrganizationDTO {

        @JsonProperty("id")
        @Schema(description = "Идентификатор записи в БД", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private final Long id;

        @JsonProperty("name")
        @Schema(description = "Название организации", requiredMode = Schema.RequiredMode.REQUIRED, example = "ООО Хихи-хаха")
        private final String name;
    }

}
