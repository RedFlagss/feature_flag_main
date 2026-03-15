package org.redflag.dto.node.get;

import org.redflag.model.OrganizationNode;

public record GetChildrenOrganizationNodesRequest(Long organizationId, Long nodeId) {
}
