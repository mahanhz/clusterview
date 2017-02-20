package org.amhzing.clusterview.backend.infra.jpa.repository;

import org.amhzing.clusterview.backend.infra.jpa.mapping.CapabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapabilityJpaRepository extends JpaRepository<CapabilityEntity, Long> {

}
