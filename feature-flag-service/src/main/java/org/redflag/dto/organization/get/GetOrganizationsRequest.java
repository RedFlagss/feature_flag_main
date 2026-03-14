package org.redflag.dto.organization.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


public record GetOrganizationsRequest(Integer limit, Integer offset){
}
