package org.redflag.controller;

import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.redflag.dto.organization.create.CreateOrganizationRequest;
import org.redflag.dto.organization.create.CreateOrganizationResponse;
import org.redflag.dto.ErrorDTO;
import org.redflag.dto.organization.get.GetOrganizationsResponse;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller("api/v1/organizations")
@Tag(name = "Организация")
public class OrganizationController {

    @Post
    @Operation(
            summary = "Создать организацию",
            description = "Cоздает организацию, если нет организации с таким же именем"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешный ответ создания",
                    content = @Content(schema = @Schema(implementation = CreateOrganizationResponse.class))
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
    public CreateOrganizationResponse create(@Body CreateOrganizationRequest request) {
        return new CreateOrganizationResponse(1L, "Хехе");
    }

    @Get
    @Operation(
            summary = "Получить список организаций",
            description = "Возвращает список организаций с пагинацией"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(schema = @Schema(implementation = GetOrganizationsResponse.class))
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
                    responseCode = "500",
                    description = "Неизвестная ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            )

    })
    public GetOrganizationsResponse getOrganizations(@QueryValue("limit") Integer limit, @QueryValue("offset") Integer offset) {
        return new GetOrganizationsResponse(List.of(new GetOrganizationsResponse.OrganizationDTO(1L, "Хохохо")), limit, offset, 1);
    }
//TODO: jsonProperty


}
