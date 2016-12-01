package org.amhzing.clusterview.infra.jpa.repository.user;

import org.amhzing.clusterview.infra.jpa.mapping.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);
}
