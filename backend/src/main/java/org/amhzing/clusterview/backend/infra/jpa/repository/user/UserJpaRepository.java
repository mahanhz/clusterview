package org.amhzing.clusterview.backend.infra.jpa.repository.user;

import org.amhzing.clusterview.backend.infra.jpa.mapping.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
}
