package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.infra.jpa.mapping.CoreActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoreActivityJpaRepository extends JpaRepository<CoreActivityEntity, String> {

}
