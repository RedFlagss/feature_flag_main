package org.redflag.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import org.redflag.entities.SdkClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface SdkClientRepository extends JpaRepository<SdkClient, Long> {

    Optional<SdkClient> findByLogin(String username);
}


