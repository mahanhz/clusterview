package org.amhzing.clusterview.backend.infra.jpa.repository;

import org.amhzing.clusterview.backend.infra.jpa.mapping.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

}
