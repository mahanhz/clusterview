package org.amhzing.clusterview.app.infra.jpa.repository;

import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.infra.jpa.mapping.CapabilityEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class CapabilityJpaRepositoryTest {

    @Autowired
    private CapabilityJpaRepository capabilityJpaRepository;

    @Test
    public void should_get_capability() throws Exception {
        final CapabilityEntity sc = capabilityJpaRepository.findOne(1L);

        assertThat(sc).isNotNull();
        assertThat(sc.getActivity().getId()).isEqualToIgnoringCase("sct");
    }
}