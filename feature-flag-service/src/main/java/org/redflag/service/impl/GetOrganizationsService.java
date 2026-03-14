package org.redflag.service.impl;

import io.micronaut.data.model.Pageable;
import io.micronaut.data.runtime.http.PageableRequestArgumentBinder;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.redflag.dto.organization.get.GetOrganizationsRequest;
import org.redflag.dto.organization.get.GetOrganizationsResponse;
import org.redflag.error.ErrorCatalog;
import org.redflag.model.Organization;
import org.redflag.repository.OrganizationRepository;
import org.redflag.service.AbstractService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Singleton
public class GetOrganizationsService extends AbstractService<GetOrganizationsRequest, GetOrganizationsResponse> {
    private final OrganizationRepository organizationRepository;
    @Override
    protected void validateRequest(GetOrganizationsRequest getOrganizationsRequest) {
        Integer limit = getOrganizationsRequest.limit();
        Integer offset = getOrganizationsRequest.offset();
        //TODO: вынести максимальный лимит и прописать в api
        if (Objects.isNull(limit) || limit <= 0 || limit > 100) {
            throw ErrorCatalog.BAD_LIMIT.getException();
        }

        if (Objects.isNull(offset) || offset < 0) {
            throw ErrorCatalog.BAD_OFFSET.getException();
        }
    }

    @Override
    protected GetOrganizationsResponse logic(GetOrganizationsRequest getOrganizationsRequest) {
        List<Organization> organizations= organizationRepository
                .findAll(getOrganizationsRequest.limit(), getOrganizationsRequest.offset());

        List<GetOrganizationsResponse.OrganizationDTO> organizationDTOS =  organizations.stream()
                .map(this::toOrganizationDTO).toList();

        if (organizationDTOS.isEmpty()) {
            throw ErrorCatalog.EMPTY_LIST.getException();
        }

        return new GetOrganizationsResponse(organizationDTOS,
                getOrganizationsRequest.limit(),
                getOrganizationsRequest.offset(),
                organizations.size());
    }

    private GetOrganizationsResponse.OrganizationDTO toOrganizationDTO(Organization organization) {
        return new GetOrganizationsResponse.OrganizationDTO(organization.getId(),
                organization.getName());
    }
}
