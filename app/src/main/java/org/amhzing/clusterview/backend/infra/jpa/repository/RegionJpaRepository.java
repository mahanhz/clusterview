package org.amhzing.clusterview.backend.infra.jpa.repository;

import org.amhzing.clusterview.backend.infra.jpa.mapping.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionJpaRepository extends JpaRepository<RegionEntity, String> {

}
