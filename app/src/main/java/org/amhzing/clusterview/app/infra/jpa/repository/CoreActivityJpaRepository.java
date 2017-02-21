package org.amhzing.clusterview.app.infra.jpa.repository;

import org.amhzing.clusterview.app.infra.jpa.mapping.CoreActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoreActivityJpaRepository extends JpaRepository<CoreActivityEntity, String> {

}
