package org.redflag.dto.node.get;

public record GetFeatureFlagByIdRequest(Long organizationId, Long nodeId, Long flagId) {
}
