package org.redflag.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import org.redflag.entities.SdkClient;

@Repository
public interface SdkClientRepository extends JpaRepository<SdkClient, Long> {
}
