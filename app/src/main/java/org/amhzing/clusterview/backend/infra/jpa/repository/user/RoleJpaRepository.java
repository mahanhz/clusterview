package org.amhzing.clusterview.backend.infra.jpa.repository.user;

import org.amhzing.clusterview.backend.infra.jpa.mapping.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);
}
