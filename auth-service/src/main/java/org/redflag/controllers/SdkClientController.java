package org.redflag.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.redflag.dto.SdkClientResponse;
import org.redflag.dto.SdkLoginUpdate;
import org.redflag.services.SdkClientService;

@Controller("/api/v1/sdk-clients")
@RequiredArgsConstructor
public class SdkClientController {

    private final SdkClientService sdkService;

    @Post
    @Status(HttpStatus.CREATED)
    public SdkClientResponse create(@Body @Valid SdkLoginUpdate request) {
        return sdkService.create(request.newLogin());
    }

    @Patch("/{id}/login")
    public SdkClientResponse updateLogin(@PathVariable Long id, @Body @Valid SdkLoginUpdate request) {
        return sdkService.updateLogin(id, request.newLogin());
    }

    @Patch("/{id}/password/regenerate")
    public SdkClientResponse regeneratePassword(@PathVariable Long id) {
        return sdkService.regeneratePassword(id);
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        sdkService.delete(id);
    }
}