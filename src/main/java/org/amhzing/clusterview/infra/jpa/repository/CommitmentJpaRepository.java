package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.infra.jpa.mapping.CommitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitmentJpaRepository extends JpaRepository<CommitmentEntity, Long> {

}
