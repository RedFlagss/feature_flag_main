package org.redflag.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.annotation.EntityGraph;
import io.micronaut.data.jpa.repository.JpaRepository;
import org.redflag.entities.UiClient;

import java.util.Optional;

@Repository
public interface UiClientRepository extends JpaRepository<UiClient, Long> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<UiClient> findByLogin(String login);

    Boolean existsByLogin(String login);
}
