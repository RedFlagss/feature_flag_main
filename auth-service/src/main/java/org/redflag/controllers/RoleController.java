package org.redflag.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.redflag.annotations.NoSdkAllowed;
import org.redflag.dto.RoleDto;
import org.redflag.entities.Role;
import org.redflag.services.RoleService;

import java.util.List;

@Controller("/api/v1/roles")
@RequiredArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED)
@NoSdkAllowed
public class RoleController {

    private final RoleService roleService;

    @Get
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

}