package org.amhzing.clusterview.backend.infra.jpa.repository;

import org.amhzing.clusterview.backend.annotation.TestOffline;
import org.amhzing.clusterview.backend.infra.jpa.mapping.CommitmentEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class CommitmentJpaRepositoryTest {

    @Autowired
    private CommitmentJpaRepository commitmentJpaRepository;

    @Test
    public void should_get_commitment() throws Exception {
        final CommitmentEntity commitment = commitmentJpaRepository.findOne(15L);

        assertThat(commitment).isNotNull();
        assertThat(commitment.getActivity().getId()).isEqualToIgnoringCase("fh");
    }
}