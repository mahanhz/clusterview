package org.amhzing.clusterview.jpa.repository;

import org.amhzing.clusterview.jpa.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<TeamEntity, Long> {

}
