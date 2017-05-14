package org.amhzing.clusterview.integrationtest.data.jpa.repository;

import org.amhzing.clusterview.integrationtest.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.data.jpa.entity.*;
import org.amhzing.clusterview.data.jpa.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class ClusterJpaRepositoryTest {

    @Autowired
    private ClusterJpaRepository clusterJpaRepository;

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private CapabilityJpaRepository capabilityJpaRepository;

    @Autowired
    private CommitmentJpaRepository commitmentJpaRepository;

    @Autowired
    private ActivityJpaRepository activityJpaRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void should_get_cluster() throws Exception {
        final ClusterEntity cluster = clusterJpaRepository.findOne("stockholm");

        assertThat(cluster).isNotNull();
        assertThat(cluster.getId()).isEqualToIgnoringCase("stockholm");
        assertThat(cluster.getTeams()).hasSize(2);
    }

    @Test
    public void should_delete_cluster() throws Exception {
        Assertions.assertThat(allActivities()).hasSize(JpaRepositoryHelper.INITIAL_ACTIVITIES_SIZE);
        assertThat(allCapabilities()).hasSize(JpaRepositoryHelper.INITIAL_CAPABILITIES_SIZE);
        Assertions.assertThat(allCommitments()).hasSize(JpaRepositoryHelper.INITIAL_COMMITMENTS_SIZE);
        Assertions.assertThat(allMembers()).hasSize(JpaRepositoryHelper.INITIAL_MEMBERS_SIZE);
        Assertions.assertThat(allTeams()).hasSize(JpaRepositoryHelper.INITIAL_TEAM_SIZE);
        assertThat(allClusters()).hasSize(JpaRepositoryHelper.INITIAL_CLUSTERS_SIZE);

        final ClusterEntity cluster = clusterJpaRepository.findOne("stockholm");
        entityManager.remove(cluster);

        assertThat(allClusters()).hasSize(JpaRepositoryHelper.INITIAL_CLUSTERS_SIZE - 1);
        assertThat(allTeams().size()).isLessThan(JpaRepositoryHelper.INITIAL_TEAM_SIZE);
        assertThat(allMembers().size()).isLessThan(JpaRepositoryHelper.INITIAL_MEMBERS_SIZE);
        assertThat(allCommitments().size()).isLessThan(JpaRepositoryHelper.INITIAL_COMMITMENTS_SIZE);
        assertThat(allCapabilities().size()).isLessThan(JpaRepositoryHelper.INITIAL_CAPABILITIES_SIZE);
        Assertions.assertThat(allActivities()).hasSize(JpaRepositoryHelper.INITIAL_ACTIVITIES_SIZE);
    }

    private List<ClusterEntity> allClusters() {
        return clusterJpaRepository.findAll();
    }

    private List<TeamEntity> allTeams() {
        return teamJpaRepository.findAll();
    }

    private List<MemberEntity> allMembers() {
        return memberJpaRepository.findAll();
    }

    private List<CommitmentEntity> allCommitments() {
        return commitmentJpaRepository.findAll();
    }

    private List<CapabilityEntity> allCapabilities() {
        return capabilityJpaRepository.findAll();
    }

    private List<ActivityEntity> allActivities() {
        return activityJpaRepository.findAll();
    }
}