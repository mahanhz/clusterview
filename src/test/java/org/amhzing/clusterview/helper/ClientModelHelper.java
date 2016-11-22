package org.amhzing.clusterview.helper;

import org.amhzing.clusterview.web.model.*;

import static com.google.common.collect.ImmutableSet.of;

public class ClientModelHelper {

    private ClientModelHelper() {
        // To prevent instantiation
    }

    public static GroupModel groupModel() {
        return GroupModel.create(1L, of(memberModel()), locationModel());
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
}