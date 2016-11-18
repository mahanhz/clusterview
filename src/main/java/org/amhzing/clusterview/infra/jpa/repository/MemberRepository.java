package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.infra.jpa.mapping.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
