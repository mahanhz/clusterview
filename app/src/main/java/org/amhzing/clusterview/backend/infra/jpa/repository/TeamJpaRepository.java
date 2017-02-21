package org.amhzing.clusterview.backend.infra.jpa.repository;

import org.amhzing.clusterview.backend.infra.jpa.mapping.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<TeamEntity, Long> {

}
