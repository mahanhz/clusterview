package org.amhzing.clusterview.infra.jpa.repository.user;

import org.amhzing.clusterview.infra.jpa.mapping.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
}
