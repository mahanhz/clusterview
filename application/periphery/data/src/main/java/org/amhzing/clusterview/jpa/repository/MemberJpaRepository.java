package org.amhzing.clusterview.jpa.repository;

import org.amhzing.clusterview.jpa.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

}
