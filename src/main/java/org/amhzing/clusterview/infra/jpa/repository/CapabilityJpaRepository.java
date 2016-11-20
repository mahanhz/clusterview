package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.infra.jpa.mapping.Capability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapabilityJpaRepository extends JpaRepository<Capability, Long> {

}
