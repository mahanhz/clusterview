package org.amhzing.clusterview.backend.infra.jpa.repository;

import org.amhzing.clusterview.backend.infra.jpa.mapping.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityJpaRepository extends JpaRepository<ActivityEntity, String> {

}
