package org.amhzing.clusterview.data.jpa.repository;

import org.amhzing.clusterview.data.jpa.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

}
