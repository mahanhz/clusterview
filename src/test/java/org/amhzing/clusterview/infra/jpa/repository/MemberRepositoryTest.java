package org.amhzing.clusterview.infra.jpa.repository;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.infra.jpa.mapping.Activity;
import org.amhzing.clusterview.infra.jpa.mapping.Capability;
import org.amhzing.clusterview.infra.jpa.mapping.Member;
import org.amhzing.clusterview.infra.jpa.mapping.Name;
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
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository repository;

    @Autowired
    private CapabilityRepository capabilityRepository;

    @Test
    public void should_get_member() throws Exception {
        final Member member = this.repository.findOne(111L);

        assertThat(member).isNotNull();
        assertThat(member.getName().getFirstName()).isEqualTo(nameFor111().getFirstName());
        assertThat(member.getName().getMiddleName()).isEqualTo(nameFor111().getMiddleName());
        assertThat(member.getName().getLastName()).isEqualTo(nameFor111().getLastName());
        assertThat(member.getName().getSuffix()).isEqualTo(nameFor111().getSuffix());
        assertThat(member.getCapability()).hasSize(3);
    }

    @Test
    public void should_insert_member_and_capabilities() throws Exception {
        final List<Capability> capabilitiesBefore = this.capabilityRepository.findAll();
        assertThat(capabilitiesBefore).hasSize(6);

        final List<Member> membersBeforeInsert = this.repository.findAll();
        assertThat(membersBeforeInsert).hasSize(3);

        final Member persistedMember = entityManager.persist(member());

        final List<Member> membersAfterInsert = this.repository.findAll();
        assertThat(membersAfterInsert).hasSize(4);

        final Member lastMember = membersAfterInsert.get(membersAfterInsert.size() - 1);
        assertThat(lastMember).isEqualTo(persistedMember);

        final List<Capability> capabilitiesAfter = this.capabilityRepository.findAll();
        assertThat(capabilitiesAfter).hasSize(7);
    }

    @Test
    public void should_delete_member_and_capabilities() throws Exception {
        final List<Capability> capabilitiesBefore = this.capabilityRepository.findAll();
        assertThat(capabilitiesBefore).hasSize(6);

        final Member member = this.repository.findOne(111L);

        entityManager.remove(member);

        final List<Member> membersAfterInsert = this.repository.findAll();
        assertThat(membersAfterInsert).hasSize(2);

        final List<Capability> capabilitiesAfter = this.capabilityRepository.findAll();
        assertThat(capabilitiesAfter).hasSize(3);
    }

    public static Member member() {
        final Member member = new Member();
        member.setName(name());
        member.setCapability(capabilities(member));

        return member;
    }

    private static ImmutableSet<Capability> capabilities(final Member member) {
        return ImmutableSet.of(Capability.create(activity(), member));
    }

    private static Activity activity() {
        return Activity.create("NO", "Nonsense");
    }

    private static Name name() {
        return Name.create("Joanne", null, "Smith", null);
    }

    private static Name nameFor111() {
        return Name.create("John", "M", "Doe", "I");
    }
}