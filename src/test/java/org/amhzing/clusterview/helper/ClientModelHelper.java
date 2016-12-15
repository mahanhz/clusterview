package org.amhzing.clusterview.helper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.amhzing.clusterview.web.model.*;

import static com.google.common.collect.ImmutableList.of;

public class ClientModelHelper {

    private ClientModelHelper() {
        // To prevent instantiation
    }

    public static GroupModel groupModel() {
        return GroupModel.create(1L, of(memberModel()), locationModel(), of(coreActivityModel()));
    }

    public static MemberModel memberModel() {
        return MemberModel.create(2L,
                             nameModel(),
                             CapabilityModel.create(of(activityModel())),
                             CommitmentModel.create(of(anotherActivityModel())));
    }

    public static MemberModel anotherMemberModel() {
        return MemberModel.create(3L,
                                  anotherNameModel(),
                                  CapabilityModel.create(of(activityModel())),
                                  CommitmentModel.create(of(anotherActivityModel())));
    }

    public static ActivityStatisticModel activityStatisticModel() {
        return ActivityStatisticModel.create(ImmutableMap.of("Study Circle", 10L),
                                             ImmutableList.of(CoreActivityModel.create("sc", "SC", 1L, 5L, 3L)));
    }

    public static NameModel nameModel() {
        return NameModel.create("John", null, "Doe", null);
    }

    public static NameModel anotherNameModel() {
        return NameModel.create("Jane", null, "Doe", null);
    }

    public static LocationModel locationModel() {
        return LocationModel.create(100,50);
    }

    public static LocationModel anotherLocationModel() {
        return LocationModel.create(75, 220);
    }

    public static ActivityModel activityModel() {
        return ActivityModel.create("sc", "Study Circle");
    }

    public static ActivityModel anotherActivityModel() {
        return ActivityModel.create("hv", "Home Visit");
    }

    public static CoreActivityModel coreActivityModel() {
        return CoreActivityModel.create("sc", "SC", 2, 10, 5);
    }

    public static CoreActivityModel anotherCoreActivityModel() {
        return CoreActivityModel.create("dm", "DM", 1, 13, 7);
    }
}
