package org.redflag.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
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
import org.redflag.dto.node.get.GetOrganizationNodeByIdResponse;
import org.redflag.dto.node.get.GetOrganizationNodesResponse;
import org.redflag.dto.node.update.UpdateOrganizationNodeRequest;
import org.redflag.dto.node.update.UpdateOrganizationNodeResponse;
import org.redflag.dto.organization.create.CreateOrganizationRequest;
import org.redflag.dto.organization.create.CreateOrganizationResponse;
import org.redflag.dto.organization.get.GetOrganizationsResponse;
import org.redflag.dto.organization.update.UpdateOrganizationRequest;
import org.redflag.dto.organization.update.UpdateOrganizationResponse;

import java.util.List;
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
    public CreateOrganizationNodeResponse createOrganizationNode(
            @Parameter(description = "Идентификатор организации", required = true, example = "1")
            @PathVariable Long organizationId,
            @Body CreateOrganizationNodeRequest request ) {
        return new CreateOrganizationNodeResponse(1L,
                organizationId,
                UUID.fromString("9c2c7a6d-29e9-4c8c-a0b3-3b14f7c2b4f1"),
                "100.1",
                "Кредитование",
                false,
                1L);
    }

    @Get("/{nodeId}")
    @Operation(
            summary = "Получить звено организации",
            description = "Получает звено организации по id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(schema = @Schema(implementation = GetOrganizationNodeByIdResponse.class))
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
                    responseCode = "500",
                    description = "Неизвестная ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            )

    })
    public GetOrganizationNodeByIdResponse getOrganizationNodeById(
            @Parameter(description = "Идентификатор организации", required = true, example = "1")
            @PathVariable Long organizationId,
            @Parameter(description = "Идентификатор звена организации", required = true, example = "1")
            @PathVariable Long nodeId) {
        return new GetOrganizationNodeByIdResponse(nodeId,
                organizationId,
                UUID.fromString("9c2c7a6d-29e9-4c8c-a0b3-3b14f7c2b4f1"),
                "100.1",
                "Кредитование",
                false,
                1L);
    }
    @Get()
    @Operation(
            summary = "Получить список звеньев организации",
            description = "Получает звенья организации с пагинацией и возможностью фильтровать по родительскому звену "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(schema = @Schema(implementation = GetOrganizationNodesResponse.class))
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
                    responseCode = "500",
                    description = "Неизвестная ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            )

    })
    public GetOrganizationNodesResponse getOrganizationNodes(
            @Parameter(description = "Идентификатор организации", required = true, example = "1")
            @PathVariable Long organizationId,
            @Parameter(description = "Идентификатор родительского звена организации", required = false, example = "100")
            @QueryValue Long parentId,
            @Parameter(description = "Верхний лимит количества записей для получения блока записей", required = true, example = "42")
            @QueryValue("limit") Integer limit,
            @Parameter(description = "Начальный номер записи от начала для получения блока записей", required = true, example = "0")
            @QueryValue("offset") Integer offset
            ) {
        return new GetOrganizationNodesResponse(List.of(new GetOrganizationNodesResponse.OrganizationNodeDTO(1L,
                organizationId,
                UUID.fromString("9c2c7a6d-29e9-4c8c-a0b3-3b14f7c2b4f1"),
                "100.1",
                "Кредитование",
                false,
                1L)),
                limit,
                offset,
                1);
    }

    @Patch("/{nodeId}")
    @Operation(
            summary = "Обновить информацию о звене организации",
            description = "Обновляет информацию у конкретного звена организации"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(schema = @Schema(implementation = UpdateOrganizationNodeResponse.class))
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
                    responseCode = "500",
                    description = "Неизвестная ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            )

    })
    public UpdateOrganizationNodeResponse updateOrganizationNode(
            @Parameter(description = "Идентификатор организации", required = true, example = "1")
            @PathVariable Long organizationId,
            @Parameter(description = "Идентификатор звена организации", required = true, example = "1")
            @PathVariable Long nodeId,
            @Body UpdateOrganizationNodeRequest updateOrganizationNodeRequest
            ) {
        return new UpdateOrganizationNodeResponse(nodeId,
                organizationId,
                UUID.fromString("9c2c7a6d-29e9-4c8c-a0b3-3b14f7c2b4f1"),
                "100.1",
                updateOrganizationNodeRequest.getName(),
                updateOrganizationNodeRequest.getIsService(),
                updateOrganizationNodeRequest.getVersion()+1);
    }
    @Delete("/{nodeId}")
    @Operation(
            summary = "Удалить звено организации",
            description = "Удаляет конкретное звено организации со всеми вложенными звеньями"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешный ответ"
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
                    responseCode = "500",
                    description = "Неизвестная ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            )

    })
    public HttpResponse<Void> deleteOrganizationNode(
            @Parameter(description = "Идентификатор организации", required = true, example = "1")
            @PathVariable Long organizationId,
            @Parameter(description = "Идентификатор звена организации", required = true, example = "1")
            @PathVariable Long nodeId
    ) {
        return HttpResponse.noContent();
    }




}
