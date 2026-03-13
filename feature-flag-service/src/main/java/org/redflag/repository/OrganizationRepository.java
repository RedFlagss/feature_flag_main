package org.redflag.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.RequiredArgsConstructor;
import org.redflag.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}
