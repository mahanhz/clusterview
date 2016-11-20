package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.infra.jpa.mapping.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterJpaRepository extends JpaRepository<Cluster, String> {

}
