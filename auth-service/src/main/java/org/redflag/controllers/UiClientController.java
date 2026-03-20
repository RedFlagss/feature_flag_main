package org.redflag.controllers;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.redflag.annotations.NoSdkAllowed;
import org.redflag.dto.UiClientDto;
import org.redflag.dto.UpdateDepartmentUiClientRequest;
import org.redflag.dto.UpdateRolesUiClientRequest;
import org.redflag.dto.UpdateUiClientRequest;
import org.redflag.services.UiClientService;

import java.util.List;
import java.util.UUID;

@Controller("/api/v1/clients")
@RequiredArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED)
@NoSdkAllowed
public class UiClientController {

    private final UiClientService clientService;

    @Get("/by-department/{uuidDepartament}")
    @Secured("UPDATE_EMPLOYEE_ROLE")
    public List<UiClientDto> getClientsByDept(@QueryValue UUID uuidDepartament) {
        return clientService.getAllByDepartment(uuidDepartament);
    }

    @Get("/me")
    public UiClientDto getMe(Authentication authentication) {
        return clientService.getByLogin(authentication.getName());
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

    @Patch("/me")
    public HttpResponse<?> updateMyCredentials(Authentication auth, @Body @Valid UpdateUiClientRequest request) {
        clientService.updateUiClient(auth.getName(), request);
        return HttpResponse.ok();
    }


    @Post("/{id}/roles/add")
    @Secured("UPDATE_EMPLOYEE_ROLE")
    public HttpResponse<?> addRoles(@PathVariable Long id, @Body @Valid UpdateRolesUiClientRequest request) {
        clientService.addRoles(id, request.roleNames());
        return HttpResponse.ok();
    }

    @Delete("/{id}/roles/remove")
    @Secured("UPDATE_EMPLOYEE_ROLE")
    public HttpResponse<?> removeRoles(@PathVariable Long id, @Body @Valid UpdateRolesUiClientRequest request) {
        clientService.removeRoles(id, request.roleNames());
        return HttpResponse.ok();
    }

    @Patch("/{id}/department")
    @Secured("UPDATE_EMPLOYEE_ROLE")
    public HttpResponse<?> updateClientDepartment(@PathVariable Long id, @Body @Valid UpdateDepartmentUiClientRequest request) {
        clientService.updateDepartment(id, request.uuidDepartament());
        return HttpResponse.ok();
    }
}