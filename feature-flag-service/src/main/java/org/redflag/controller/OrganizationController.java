package org.redflag.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import org.redflag.dto.CreateOrganizationRequest;
import org.redflag.dto.CreateOrganizationResponse;

@Controller("api/v1/organizations")
public class OrganizationController {

    @Post()
    public CreateOrganizationResponse create(CreateOrganizationRequest dto) {
        return new CreateOrganizationResponse();
    }


}
