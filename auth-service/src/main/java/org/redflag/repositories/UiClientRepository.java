package org.redflag.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import org.redflag.entities.UiClient;

@Repository
public interface UiClientRepository extends JpaRepository<UiClient, Long> {

}
