package org.amhzing.clusterview.data.jpa.repository;

import org.amhzing.clusterview.data.jpa.entity.CommitmentEntity;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
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