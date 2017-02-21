package org.amhzing.clusterview.app.infra.jpa.repository;

import org.amhzing.clusterview.app.infra.jpa.mapping.CapabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapabilityJpaRepository extends JpaRepository<CapabilityEntity, Long> {

}
