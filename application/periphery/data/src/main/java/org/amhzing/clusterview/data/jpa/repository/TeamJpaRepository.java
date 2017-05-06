package org.amhzing.clusterview.data.jpa.repository;

import org.amhzing.clusterview.data.jpa.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<TeamEntity, Long> {

}
