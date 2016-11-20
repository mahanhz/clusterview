package org.amhzing.clusterview.helper;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.infra.jpa.mapping.*;

public final class JpaRepositoryHelper {

    public static final int INITIAL_ACTIVITIES_SIZE = 5;
    public static final int INITIAL_CLUSTERS_SIZE = 3;
    public static final int INITIAL_TEAM_SIZE = 2;
    public static final int INITIAL_MEMBERS_SIZE = 3;
    public static final int INITIAL_CAPABILITIES_SIZE = 6;
    public static final int INITIAL_COMMITMENTS_SIZE = 4;


    private JpaRepositoryHelper() {
        // To prevent instantiation
    }


    public static Member member() {
        final Member member = new Member();
        member.setName(name());
        member.setCapabilities(capabilities(member));
        member.setCommitments(commitments(member));

        return member;
    }

    public static ImmutableSet<Capability> capabilities(final Member member) {
        return ImmutableSet.of(capability(member));
    }

    public static Capability capability(final Member member) {
        return Capability.create(newActivity(), member);
    }

    public static ImmutableSet<Commitment> commitments(final Member member) {
        return ImmutableSet.of(Commitment.create(anotherNewActivity(), member));
    }

    public static ActivityEntity newActivity() {
        return ActivityEntity.create("NO", "Nonsense");
    }

    public static ActivityEntity anotherNewActivity() {
        return ActivityEntity.create("YE", "Something");
    }

    public static Name name() {
        return Name.create("Joanne", null, "Smith", null);
    }

    public static Name nameFor111() {
        return Name.create("John", "M", "Doe", "I");
    }
}
