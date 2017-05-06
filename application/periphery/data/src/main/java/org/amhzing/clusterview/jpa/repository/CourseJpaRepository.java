package org.amhzing.clusterview.jpa.repository;

import org.amhzing.clusterview.jpa.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaRepository extends JpaRepository<CourseEntity, String> {

}
