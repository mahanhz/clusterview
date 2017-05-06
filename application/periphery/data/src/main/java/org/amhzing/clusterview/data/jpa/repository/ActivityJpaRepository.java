package org.amhzing.clusterview.data.jpa.repository;

import org.amhzing.clusterview.data.jpa.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityJpaRepository extends JpaRepository<ActivityEntity, String> {

}
