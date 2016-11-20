package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.infra.jpa.mapping.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionJpaRepository extends JpaRepository<RegionEntity, String> {

}
