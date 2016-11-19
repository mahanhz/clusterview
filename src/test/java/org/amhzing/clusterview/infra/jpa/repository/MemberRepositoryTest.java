package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.infra.jpa.mapping.Activity;
import org.amhzing.clusterview.infra.jpa.mapping.Capability;
import org.amhzing.clusterview.infra.jpa.mapping.Commitment;
import org.amhzing.clusterview.infra.jpa.mapping.Member;
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
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CapabilityRepository capabilityRepository;

    @Autowired
    private CommitmentRepository commitmentRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    public void should_get_member() throws Exception {
        final Member member = this.memberRepository.findOne(111L);

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

        final Member persistedMember = entityManager.persist(member());

        final List<Member> membersAfterInsert = allMembers();
        assertThat(allMembers()).hasSize(INITIAL_MEMBERS_SIZE + 1);
        final Member lastMember = membersAfterInsert.get(membersAfterInsert.size() - 1);
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

        final Member member = this.memberRepository.findOne(111L);
        entityManager.remove(member);

        final List<Member> membersAfterDelete = allMembers();
        assertThat(membersAfterDelete).hasSize(INITIAL_MEMBERS_SIZE -1);

        assertThat(allCommitments()).hasSize(INITIAL_COMMITMENTS_SIZE - 1);
        assertThat(allCapabilities()).hasSize(INITIAL_CAPABILITIES_SIZE - 3);
        assertThat(allActivities()).hasSize(INITIAL_ACTIVITIES_SIZE);
    }

    private List<Member> allMembers() {
        return memberRepository.findAll();
    }

    private List<Commitment> allCommitments() {
        return commitmentRepository.findAll();
    }

    private List<Capability> allCapabilities() {
        return capabilityRepository.findAll();
    }

    private List<Activity> allActivities() {
        return activityRepository.findAll();
    }
}