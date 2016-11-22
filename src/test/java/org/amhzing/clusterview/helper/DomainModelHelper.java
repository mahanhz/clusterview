package org.amhzing.clusterview.helper;

import org.amhzing.clusterview.domain.model.*;

import static com.google.common.collect.ImmutableSet.of;

public final class DomainModelHelper {

    private static final Group.Id GROUP_1 = Group.Id.create(123L);
    private static final Group.Id GROUP_2 = Group.Id.create(456L);
    private static final Cluster.Id CLUSTER_1 = Cluster.Id.create("Cluster1");
    private static final Cluster.Id CLUSTER_2 = Cluster.Id.create("Cluster2");
    private static final Region.Id REGION_1 = Region.Id.create("Region1");
    private static final Region.Id REGION_2 = Region.Id.create("Region2");

    private static final Member.Id MEMBER_1 = Member.Id.create(789L);
    private static final Member.Id MEMBER_2 = Member.Id.create(101L);

    private DomainModelHelper() {
        // To prevent instantiation
    }

    public static Region region() {
        return Region.create(regionId(), of(cluster()));
    }

    public static Region anotherRegion() {
        return Region.create(anotherRegionId(), of(anotherCluster()));
    }

    public static Cluster cluster() {
        return Cluster.create(clusterId(), of(group()));
    }

    public static Cluster anotherCluster() {
        return Cluster.create(anotherClusterId(), of(anotherGroup()));
    }

    public static Group group() {
        return Group.create(groupId(), of(member()), location());
    }

    public static Group anotherGroup() {
        return Group.create(anotherGroupId(), of(anotherMember()), anotherLocation());
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
                             Capability.create(of(activity())),
                             Commitment.create(of(anotherActivity())));
    }

    public static Member anotherMember() {
        return Member.create(anotherMemberId(),
                             anotherName(),
                             Capability.create(of(activity())),
                             Commitment.create(of(anotherActivity())));
    }

    public static Activity activity() {
        return Activity.create(Activity.Id.create("sc"), "Study Circle");
    }

    public static Activity anotherActivity() {
        return Activity.create(Activity.Id.create("hv"), "Home Visit");
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
