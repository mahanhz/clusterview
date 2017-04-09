package org.amhzing.clusterview.app.infra.jpa.repository.user;

import org.amhzing.clusterview.app.infra.jpa.mapping.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);
}
