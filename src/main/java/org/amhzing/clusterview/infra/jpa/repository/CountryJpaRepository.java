package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.infra.jpa.mapping.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryJpaRepository extends JpaRepository<Country, String> {

}
