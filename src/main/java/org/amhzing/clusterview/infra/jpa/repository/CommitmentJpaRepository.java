package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.infra.jpa.mapping.Commitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitmentJpaRepository extends JpaRepository<Commitment, Long> {

}
