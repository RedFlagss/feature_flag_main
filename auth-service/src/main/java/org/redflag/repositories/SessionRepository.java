package org.redflag.repositories;


import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import org.redflag.entities.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

}
