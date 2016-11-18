package org.amhzing.clusterview.helper;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.domain.model.*;

import static com.google.common.collect.ImmutableSet.of;
import static org.amhzing.clusterview.domain.model.Activity.HOME_VISIT;
import static org.amhzing.clusterview.domain.model.Activity.STUDY_CIRCLE;

public final class DomainModelHelper {

    private static final Group.Id GROUP_1 = Group.Id.create("Group1");
    private static final Group.Id GROUP_2 = Group.Id.create("Group2");
    private static final Cluster.Id CLUSTER_1 = Cluster.Id.create("Cluster1");
    private static final Cluster.Id CLUSTER_2 = Cluster.Id.create("Cluster2");
    private static final Region.Id REGION_1 = Region.Id.create("Region1");
    private static final Region.Id REGION_2 = Region.Id.create("Region2");

    private DomainModelHelper() {
        // To prevent instantiation
    }

    private static final Member.Id MEMBER_1 = Member.Id.create("member1");
    private static final Member.Id MEMBER_2 = Member.Id.create("member2");

    public static Cluster cluster() {
        return Cluster.create(clusterId(), ImmutableSet.of(groupId()));
    }

    public static Cluster anotherCluster() {
        return Cluster.create(anotherClusterId(), ImmutableSet.of(anotherGroupId()));
    }

    public static Group group() {
        return Group.create(groupId(), ImmutableSet.of(memberId()), location());
    }

    public static Group anotherGroup() {
        return Group.create(anotherGroupId(), ImmutableSet.of(anotherMemberId()), anotherLocation());
    }

    public static Location location() {
        return Location.create(100,50);
    }

    public static Location anotherLocation() {
        return Location.create(75, 220);
    }

    public static Member member() {
        return Member.create(memberId(),
                             name(),
                             Capability.create(of(STUDY_CIRCLE)),
                             Commitment.create(of(HOME_VISIT)));
    }

    public static Member anotherMember() {
        return Member.create(anotherMemberId(),
                             anotherName(),
                             Capability.create(of(STUDY_CIRCLE)),
                             Commitment.create(of(HOME_VISIT)));
    }

    public static Name name() {
        return Name.create(firstName(), null, lastName(), null);
    }

    public static Name anotherName() {
        return Name.create(FirstName.create("Jane"), null, LastName.create("Doe"), null);
    }

    public static FirstName firstName() {
        return FirstName.create("firstName");
    }

    public static MiddleName middleName() {
        return MiddleName.create("middleName");
    }

    public static LastName lastName() {
        return LastName.create("lastName");
    }

    public static Suffix suffix() {
        return Suffix.create("suffix");
    }

    public static Member.Id memberId() {
        return MEMBER_1;
    }

    public static Member.Id anotherMemberId() {
        return MEMBER_2;
    }

    public static Group.Id groupId() {
        return GROUP_1;
    }

    public static Group.Id anotherGroupId() {
        return GROUP_2;
    }

    public static Cluster.Id clusterId() {
        return CLUSTER_1;
    }

    public static Cluster.Id anotherClusterId() {
        return CLUSTER_2;
    }

    public static Region.Id regionId() {
        return REGION_1;
    }

    public static Region.Id anotherRegionId() {
        return REGION_2;
    }
}
