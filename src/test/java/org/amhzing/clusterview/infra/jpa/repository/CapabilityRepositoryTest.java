package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.infra.jpa.mapping.Capability;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class CapabilityRepositoryTest {

    @Autowired
    private CapabilityRepository capabilityRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void should_get_capability() throws Exception {
        final Capability sc = capabilityRepository.findOne(1L);

        assertThat(sc).isNotNull();
        assertThat(sc.getActivity().getId()).isEqualTo("SC");
    }
}