package org.amhzing.clusterview.jpa.repository;

import org.amhzing.clusterview.jpa.entity.ClusterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterJpaRepository extends JpaRepository<ClusterEntity, String> {

}
