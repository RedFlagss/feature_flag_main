package org.redflag.service.impl;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.redflag.dto.featureflag.FeatureFlagDTO;
import org.redflag.dto.featureflag.FeatureFlagIdDTO;
import org.redflag.error.ErrorCatalog;
import org.redflag.model.FeatureFlag;
import org.redflag.repository.FeatureFlagRepository;
import org.redflag.service.BaseService;

@Singleton
@RequiredArgsConstructor
public class GetFeatureFlagByIdService extends BaseService<FeatureFlagIdDTO, FeatureFlagDTO> {
    private final FeatureFlagRepository featureFlagRepository;

    @Override
    protected FeatureFlagDTO execute(FeatureFlagIdDTO request) {
        FeatureFlag featureFlag = featureFlagRepository.findById(request.getFlagId())
                .orElseThrow(ErrorCatalog.NO_DATA::getException);
        return FeatureFlagDTO.builder()
                .id(featureFlag.getId())
                .nodeId(featureFlag.getOrganizationNode().getId())
                .name(featureFlag.getName())
                .value(featureFlag.getValue())
                .version(featureFlag.getVersion())
                .build();
    }
}
