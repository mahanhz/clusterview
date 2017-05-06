package org.amhzing.clusterview.jpa.repository.user;

import org.amhzing.clusterview.jpa.entity.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);
}
