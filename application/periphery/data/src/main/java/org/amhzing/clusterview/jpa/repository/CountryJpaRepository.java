package org.amhzing.clusterview.jpa.repository;

import org.amhzing.clusterview.jpa.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryJpaRepository extends JpaRepository<CountryEntity, String> {

}
