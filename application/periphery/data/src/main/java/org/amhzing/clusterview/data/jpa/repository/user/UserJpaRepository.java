package org.amhzing.clusterview.data.jpa.repository.user;

import org.amhzing.clusterview.data.jpa.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);
}
