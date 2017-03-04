package org.amhzing.clusterview.app.infra.jpa.repository;

import org.amhzing.clusterview.app.infra.jpa.mapping.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaRepository extends JpaRepository<CourseEntity, String> {

}
