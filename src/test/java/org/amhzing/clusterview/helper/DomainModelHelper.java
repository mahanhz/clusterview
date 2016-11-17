package org.amhzing.clusterview.helper;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.domain.model.*;

import static com.google.common.collect.ImmutableSet.of;
import static org.amhzing.clusterview.domain.model.Activity.HOME_VISIT;
import static org.amhzing.clusterview.domain.model.Activity.STUDY_CIRCLE;

public final class DomainModelHelper {

    private DomainModelHelper() {
        // To prevent instantiation
    }

    public static Cluster cluster() {
        return Cluster.create("Cluster1", ImmutableSet.of(group()));
    }

    public static Cluster anotherCluster() {
        return Cluster.create("Cluster2", ImmutableSet.of(anotherGroup()));
    }

    public static Group group() {
        return Group.create(ImmutableSet.of(member()), location());
    }

    public static Group anotherGroup() {
        return Group.create(ImmutableSet.of(anotherMember()), anotherLocation());
    }

    public static Location location() {
        return Location.create(100,50);
    }

    public static Location anotherLocation() {
        return Location.create(75, 220);
    }

    public static Member member() {
        return Member.create(name(), Capability.create(of(STUDY_CIRCLE)), Commitment.create(of(HOME_VISIT)));
    }

    public static Member anotherMember() {
        return Member.create(anotherName(), Capability.create(of(STUDY_CIRCLE)), Commitment.create(of(HOME_VISIT)));
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
}
