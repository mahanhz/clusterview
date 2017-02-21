package org.amhzing.clusterview.app.infra.jpa.repository;

import org.amhzing.clusterview.app.infra.jpa.mapping.ClusterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterJpaRepository extends JpaRepository<ClusterEntity, String> {

}
