package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterJpaRepository extends JpaRepository<ClusterEntity, String> {

}
