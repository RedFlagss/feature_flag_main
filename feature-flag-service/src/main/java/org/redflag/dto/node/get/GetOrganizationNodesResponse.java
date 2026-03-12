package org.redflag.dto.node.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.redflag.dto.organization.get.GetOrganizationsResponse;

import java.util.List;
import java.util.UUID;

@Data
@Introspected
@Serdeable
public class GetOrganizationNodesResponse {
    @JsonProperty("items")
    @Schema(description = "Массив организаций", requiredMode = Schema.RequiredMode.REQUIRED)
    private final List<OrganizationNodeDTO> items;
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
    public static class OrganizationNodeDTO{
        @JsonProperty("id")
        @Schema(description = "Идентификатор записи в БД", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private final Long id;
        @JsonProperty("organizationId")
        @Schema(description = "Идентификатор организации, к которой принадлежит звено, в БД", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private final Long organizationId;
        @JsonProperty("uuid")
        @Schema(description = "Не технический идентификатор звена организации", requiredMode = Schema.RequiredMode.REQUIRED, example = "9c2c7a6d-29e9-4c8c-a0b3-3b14f7c2b4f1")
        private final UUID uuid;
        @JsonProperty("path")
        @Schema(description = "Путь из идентификаторов от корня организации до текущего звена", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.1")
        private final String path;
        @JsonProperty("name")
        @Schema(description = "Название звена организации", requiredMode = Schema.RequiredMode.REQUIRED, example = "Кредитование")
        private final String name;
        @JsonProperty("isService")
        @Schema(description = "Является ли данное подразделение сервисом", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        private final Boolean isService;
        @JsonProperty("version")
        @Schema(description = "Версия данных для оптимистичной блокировки", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private final Long version;
    }
}
