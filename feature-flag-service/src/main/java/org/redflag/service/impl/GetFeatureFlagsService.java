package org.redflag.service.impl;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.redflag.dto.featureflag.FeatureFlagDTO;
import org.redflag.dto.featureflag.get.GetFeatureFlagsRequest;
import org.redflag.dto.featureflag.get.GetFeatureFlagsResponse;
import org.redflag.error.ErrorCatalog;
import org.redflag.model.FeatureFlag;
import org.redflag.repository.FeatureFlagRepository;
import org.redflag.service.BaseService;
import org.redflag.validator.PaginationParameterValidator;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class GetFeatureFlagsService extends BaseService<GetFeatureFlagsRequest, GetFeatureFlagsResponse> {
    private final FeatureFlagRepository featureFlagRepository;

    @Override
    protected void validateRequest(GetFeatureFlagsRequest request) {
        if (!PaginationParameterValidator.validateLimit(request.getLimit())) {
            throw ErrorCatalog.BAD_LIMIT.getException();
        }
        if (!PaginationParameterValidator.validateOffset(request.getOffset())) {
            throw ErrorCatalog.BAD_OFFSET.getException();
        }
    }

    @Override
    protected GetFeatureFlagsResponse execute(GetFeatureFlagsRequest request) {
        List<FeatureFlag> featureFlags = featureFlagRepository
                .findByOrganizationNodeId(request.getNodeId(), request.getLimit(), request.getOffset());
        if (featureFlags.isEmpty()) {
            throw ErrorCatalog.NO_DATA.getException();
        }

        return GetFeatureFlagsResponse.builder()
                .nodeId(request.getNodeId())
                .items(featureFlags.stream().map(this::toFeatureFlag).toList())
                .limit(request.getLimit())
                .offset(request.getOffset())
                .total(featureFlags.size())
                .build();
    }

    private FeatureFlagDTO toFeatureFlag(FeatureFlag featureFlag) {
        return FeatureFlagDTO.builder()
                .id(featureFlag.getId())
                .nodeId(featureFlag.getOrganizationNode().getId())
                .name(featureFlag.getName())
                .value(featureFlag.getValue())
                .version(featureFlag.getVersion())
                .build();
    }


}
