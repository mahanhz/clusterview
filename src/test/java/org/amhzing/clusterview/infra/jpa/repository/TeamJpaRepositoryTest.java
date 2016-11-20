package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.infra.jpa.mapping.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class TeamJpaRepositoryTest {

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
        final Team team = teamJpaRepository.findOne(901L);

        assertThat(team).isNotNull();
        assertThat(team.getMembers()).hasSize(2);
        assertThat(team.getCluster().getId()).isEqualToIgnoringCase("stockholm");
    }

    @Test
    public void should_delete_team() throws Exception {
        assertThat(allActivities()).hasSize(INITIAL_ACTIVITIES_SIZE);
        assertThat(allCapabilities()).hasSize(INITIAL_CAPABILITIES_SIZE);
        assertThat(allCommitments()).hasSize(INITIAL_COMMITMENTS_SIZE);
        assertThat(allMembers()).hasSize(INITIAL_MEMBERS_SIZE);

        final Team team = teamJpaRepository.findOne(901L);
        entityManager.remove(team);

        assertThat(allTeams()).hasSize(INITIAL_TEAM_SIZE - 1);
        assertThat(allMembers().size()).isLessThan(INITIAL_MEMBERS_SIZE);
        assertThat(allCommitments().size()).isLessThan(INITIAL_COMMITMENTS_SIZE);
        assertThat(allCapabilities().size()).isLessThan(INITIAL_CAPABILITIES_SIZE);
        assertThat(allActivities()).hasSize(INITIAL_ACTIVITIES_SIZE);
    }

    private List<Team> allTeams() {
        return teamJpaRepository.findAll();
    }

    private List<Member> allMembers() {
        return memberJpaRepository.findAll();
    }

    private List<Commitment> allCommitments() {
        return commitmentJpaRepository.findAll();
    }

    private List<Capability> allCapabilities() {
        return capabilityJpaRepository.findAll();
    }

    private List<Activity> allActivities() {
        return activityJpaRepository.findAll();
    }
}