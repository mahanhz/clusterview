package org.amhzing.clusterview.app.helper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.amhzing.clusterview.app.web.model.*;
import org.amhzing.clusterview.app.web.model.form.CourseStatisticsForm;

import static com.google.common.collect.ImmutableList.of;

public class ClientModelHelper {

    private ClientModelHelper() {
        // To prevent instantiation
    }

    public static GroupModel groupModel() {
        return GroupModel.create("3o97MmbN", of(memberModel()), locationModel(), of(coreActivityModel()));
    }

    public static GroupModel groupModel(final String obfuscatedId) {
        return GroupModel.create(obfuscatedId, of(memberModel(obfuscatedId)), locationModel(), of(coreActivityModel()));
    }

    public static MemberModel memberModel() {
        return MemberModel.create("4o97MmbN",
                                  nameModel(),
                                  CapabilityModel.create(of(activityModel())),
                                  CommitmentModel.create(of(anotherActivityModel())));
    }

    public static MemberModel memberModel(final String obfuscatedId) {
        return MemberModel.create(obfuscatedId,
                                  nameModel(),
                                  CapabilityModel.create(of(activityModel())),
                                  CommitmentModel.create(of(anotherActivityModel())));
    }


    public static MemberModel anotherMemberModel() {
        return MemberModel.create("5o97MmbN",
                                  anotherNameModel(),
                                  CapabilityModel.create(of(activityModel())),
                                  CommitmentModel.create(of(anotherActivityModel())));
    }

    public static ActivityStatisticModel activityStatisticModel() {
        return ActivityStatisticModel.create(ImmutableMap.of("Study Circle", 10L),
                                             ImmutableList.of(CoreActivityModel.create("sc", "SC", 1L, 5L, 3L)));
    }

    public static CourseStatisticModel courseStatisticModel(final String id, final String name, final int quantity) {
        return CourseStatisticModel.create(id, name, quantity);
    }

    public static CourseStatisticsForm courseStatisticsForm() {
        return CourseStatisticsForm.create(ImmutableList.of(courseStatisticModel("1", "Book 1", 125),
                                                            courseStatisticModel("2", "Book 2", 100),
                                                            courseStatisticModel("3", "Book 3", 90),
                                                            courseStatisticModel("4", "Book 4", 80),
                                                            courseStatisticModel("5", "Book 5", 70),
                                                            courseStatisticModel("6", "Book 6", 60),
                                                            courseStatisticModel("7", "Book 7", 50),
                                                            courseStatisticModel("8", "Book 8", 30),
                                                            courseStatisticModel("10", "Book 10", 10)));
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
