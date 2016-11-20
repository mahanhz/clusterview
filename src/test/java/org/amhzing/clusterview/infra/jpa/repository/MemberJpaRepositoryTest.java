package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.infra.jpa.mapping.ActivityEntity;
import org.amhzing.clusterview.infra.jpa.mapping.CapabilityEntity;
import org.amhzing.clusterview.infra.jpa.mapping.CommitmentEntity;
import org.amhzing.clusterview.infra.jpa.mapping.MemberEntity;
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
public class MemberJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private CapabilityJpaRepository capabilityJpaRepository;

    @Autowired
    private CommitmentJpaRepository commitmentJpaRepository;

    @Autowired
    private ActivityJpaRepository activityJpaRepository;

    @Test
    public void should_get_member() throws Exception {
        final MemberEntity member = this.memberJpaRepository.findOne(111L);

        assertThat(member).isNotNull();
        assertThat(member.getName().getFirstName()).isEqualTo(nameFor111().getFirstName());
        assertThat(member.getName().getMiddleName()).isEqualTo(nameFor111().getMiddleName());
        assertThat(member.getName().getLastName()).isEqualTo(nameFor111().getLastName());
        assertThat(member.getName().getSuffix()).isEqualTo(nameFor111().getSuffix());
        assertThat(member.getCapabilities()).hasSize(3);
    }

    @Test
    public void should_insert_member_and_capabilities_and_commitments_and_activity() throws Exception {
        assertThat(allActivities()).hasSize(INITIAL_ACTIVITIES_SIZE);
        assertThat(allCapabilities()).hasSize(INITIAL_CAPABILITIES_SIZE);
        assertThat(allCommitments()).hasSize(INITIAL_COMMITMENTS_SIZE);
        assertThat(allMembers()).hasSize(INITIAL_MEMBERS_SIZE);

        final MemberEntity persistedMember = entityManager.persist(member());

        final List<MemberEntity> membersAfterInsert = allMembers();
        assertThat(allMembers()).hasSize(INITIAL_MEMBERS_SIZE + 1);
        final MemberEntity lastMember = membersAfterInsert.get(membersAfterInsert.size() - 1);
        assertThat(lastMember).isEqualTo(persistedMember);

        assertThat(allCommitments()).hasSize(INITIAL_COMMITMENTS_SIZE + 1);
        assertThat(allCapabilities()).hasSize(INITIAL_CAPABILITIES_SIZE + 1);
        assertThat(allActivities()).hasSize(INITIAL_ACTIVITIES_SIZE + 2);
    }

    @Test
    public void should_delete_member_and_capabilities_and_commitments_but_not_activity() throws Exception {
        assertThat(allActivities()).hasSize(INITIAL_ACTIVITIES_SIZE);
        assertThat(allCapabilities()).hasSize(INITIAL_CAPABILITIES_SIZE);
        assertThat(allCommitments()).hasSize(INITIAL_COMMITMENTS_SIZE);
        assertThat(allMembers()).hasSize(INITIAL_MEMBERS_SIZE);

        final MemberEntity member = this.memberJpaRepository.findOne(111L);
        entityManager.remove(member);

        assertThat(allMembers()).hasSize(INITIAL_MEMBERS_SIZE -1);
        assertThat(allCommitments()).hasSize(INITIAL_COMMITMENTS_SIZE - 1);
        assertThat(allCapabilities()).hasSize(INITIAL_CAPABILITIES_SIZE - 3);
        assertThat(allActivities()).hasSize(INITIAL_ACTIVITIES_SIZE);
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