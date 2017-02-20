package org.amhzing.clusterview.backend.infra.jpa.repository;

import org.amhzing.clusterview.backend.infra.jpa.mapping.CommitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitmentJpaRepository extends JpaRepository<CommitmentEntity, Long> {

}
