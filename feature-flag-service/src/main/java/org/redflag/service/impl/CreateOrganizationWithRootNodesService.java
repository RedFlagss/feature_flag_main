package org.redflag.service.impl;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.redflag.dto.complex.CreateOrganizationWithRootNodeRequest;
import org.redflag.dto.complex.CreateOrganizationWithRootNodeResponse;
import org.redflag.dto.node.create.CreateOrganizationNodeRequest;
import org.redflag.dto.node.create.CreateOrganizationNodeResponse;
import org.redflag.dto.organization.OrganizationDTO;
import org.redflag.dto.organization.create.CreateOrganizationRequest;
import org.redflag.service.BaseService;

@RequiredArgsConstructor
@Singleton
public class CreateOrganizationWithRootNodesService extends BaseService<CreateOrganizationWithRootNodeRequest, CreateOrganizationWithRootNodeResponse> {
    private final CreateOrganizationNodeService createOrganizationNodeService;
    private final CreateOrganizationService createOrganizationService;

    @Override
    protected CreateOrganizationWithRootNodeResponse execute(CreateOrganizationWithRootNodeRequest request) {
        OrganizationDTO organizationDTO = createOrganizationService
                .service(new CreateOrganizationRequest(request.getName()));
        CreateOrganizationNodeRequest createOrganizationNodeRequest = new CreateOrganizationNodeRequest(request.getName(),
                false,
                null);
        createOrganizationNodeRequest.setOrganizationId(organizationDTO.getId());
        CreateOrganizationNodeResponse createNodeResponse = createOrganizationNodeService
                .service(createOrganizationNodeRequest);
        return new CreateOrganizationWithRootNodeResponse(organizationDTO.getId(),
                organizationDTO.getName(),
                new CreateOrganizationWithRootNodeResponse.OrganizationNodeDTO(
                        createNodeResponse.getId(),
                        createNodeResponse.getOrganizationId(),
                        createNodeResponse.getUuid(),
                        createNodeResponse.getPath(),
                        createNodeResponse.getName(),
                        createNodeResponse.getIsService(),
                        createNodeResponse.getVersion()
                ));
    }
}
