package org.amhzing.clusterview.backend.infra.jpa.repository;

import org.amhzing.clusterview.backend.annotation.TestOffline;
import org.amhzing.clusterview.backend.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.backend.infra.jpa.mapping.*;
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
public class TeamEntityJpaRepositoryTest {

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
    public void should_get_team() throws Exception {
        final TeamEntity team = teamJpaRepository.findOne(901L);

        assertThat(team).isNotNull();
        assertThat(team.getMembers()).hasSize(2);
        assertThat(team.getCluster().getId()).isEqualToIgnoringCase("stockholm");
    }

    @Test
    public void should_insert_team() throws Exception {
        assertThat(allActivities()).hasSize(JpaRepositoryHelper.INITIAL_ACTIVITIES_SIZE);
        assertThat(allCapabilities()).hasSize(JpaRepositoryHelper.INITIAL_CAPABILITIES_SIZE);
        Assertions.assertThat(allCommitments()).hasSize(JpaRepositoryHelper.INITIAL_COMMITMENTS_SIZE);
        assertThat(allMembers()).hasSize(JpaRepositoryHelper.INITIAL_MEMBERS_SIZE);
        assertThat(allTeams()).hasSize(JpaRepositoryHelper.INITIAL_TEAM_SIZE);

        entityManager.persist(JpaRepositoryHelper.teamEntity());

        assertThat(allTeams()).hasSize(JpaRepositoryHelper.INITIAL_TEAM_SIZE + 1);
        assertThat(allMembers().size()).isGreaterThan(JpaRepositoryHelper.INITIAL_MEMBERS_SIZE);
        assertThat(allCommitments().size()).isGreaterThan(JpaRepositoryHelper.INITIAL_COMMITMENTS_SIZE);
        assertThat(allCapabilities().size()).isGreaterThan(JpaRepositoryHelper.INITIAL_CAPABILITIES_SIZE);
        assertThat(allActivities().size()).isGreaterThan(JpaRepositoryHelper.INITIAL_ACTIVITIES_SIZE);
    }

    @Test
    public void should_delete_team() throws Exception {
        assertThat(allActivities()).hasSize(JpaRepositoryHelper.INITIAL_ACTIVITIES_SIZE);
        assertThat(allCapabilities()).hasSize(JpaRepositoryHelper.INITIAL_CAPABILITIES_SIZE);
        Assertions.assertThat(allCommitments()).hasSize(JpaRepositoryHelper.INITIAL_COMMITMENTS_SIZE);
        assertThat(allMembers()).hasSize(JpaRepositoryHelper.INITIAL_MEMBERS_SIZE);
        assertThat(allTeams()).hasSize(JpaRepositoryHelper.INITIAL_TEAM_SIZE);

        final TeamEntity team = teamJpaRepository.findOne(901L);
        entityManager.remove(team);

        assertThat(allTeams()).hasSize(JpaRepositoryHelper.INITIAL_TEAM_SIZE - 1);
        assertThat(allMembers().size()).isLessThan(JpaRepositoryHelper.INITIAL_MEMBERS_SIZE);
        assertThat(allCommitments().size()).isLessThan(JpaRepositoryHelper.INITIAL_COMMITMENTS_SIZE);
        assertThat(allCapabilities().size()).isLessThan(JpaRepositoryHelper.INITIAL_CAPABILITIES_SIZE);
        assertThat(allActivities()).hasSize(JpaRepositoryHelper.INITIAL_ACTIVITIES_SIZE);
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