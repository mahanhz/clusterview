package org.amhzing.clusterview.backend.infra.jpa.repository;

import org.amhzing.clusterview.backend.infra.jpa.mapping.CoreActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoreActivityJpaRepository extends JpaRepository<CoreActivityEntity, String> {

}
