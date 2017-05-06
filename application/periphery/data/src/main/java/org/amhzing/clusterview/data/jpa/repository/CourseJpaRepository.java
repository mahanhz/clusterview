package org.amhzing.clusterview.data.jpa.repository;

import org.amhzing.clusterview.data.jpa.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaRepository extends JpaRepository<CourseEntity, String> {

}
