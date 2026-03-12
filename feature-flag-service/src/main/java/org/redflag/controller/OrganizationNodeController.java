package org.redflag.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.redflag.dto.ErrorDTO;
import org.redflag.dto.node.create.CreateOrganizationNodeRequest;
import org.redflag.dto.node.create.CreateOrganizationNodeResponse;
import org.redflag.dto.organization.create.CreateOrganizationRequest;
import org.redflag.dto.organization.create.CreateOrganizationResponse;

import java.util.UUID;

@Controller("api/v1/organizations/{organizationId}/nodes")
@Tag(name = "Звено организации")
public class OrganizationNodeController {
    @Post
    @Operation(
            summary = "Создать звено организации",
            description = "Cоздает звено организации, если нет звена с таким же именем в этой организации"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешный ответ создания",
                    content = @Content(schema = @Schema(implementation = CreateOrganizationNodeResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные запроса",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Запрос без авторизации",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Недостаточно прав для выполнения этого действия",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не найдено",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Конфликт данных",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Ошибка обработки сущности",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Неизвестная ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            )

    })
    public CreateOrganizationNodeResponse create(
            @Parameter(description = "Идентификатор организации", required = true, example = "1")
            @PathVariable String organizationId,
            @Body CreateOrganizationNodeRequest request ) {
        return new CreateOrganizationNodeResponse(1L,
                1L,
                UUID.fromString("9c2c7a6d-29e9-4c8c-a0b3-3b14f7c2b4f1"),
                "100.1",
                "Кредитование",
                false,
                1L);
    }

}
