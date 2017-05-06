package org.amhzing.clusterview.data.jpa.repository;

import org.amhzing.clusterview.data.jpa.entity.ClusterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterJpaRepository extends JpaRepository<ClusterEntity, String> {

}
