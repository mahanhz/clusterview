package org.amhzing.clusterview.integrationtest.helper;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.adapter.web.api.GroupDTO;
import org.amhzing.clusterview.adapter.web.api.GroupsDTO;
import org.amhzing.clusterview.adapter.web.util.GroupDtoFactory;
import org.amhzing.clusterview.core.domain.*;

import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.group;

public final class AdapterHelper {

    private static final Group.Id GROUP_1 = Group.Id.create(123L);
    private static final Group.Id GROUP_2 = Group.Id.create(456L);
    private static final Cluster.Id CLUSTER_1 = Cluster.Id.create("Cluster1");
    private static final Cluster.Id CLUSTER_2 = Cluster.Id.create("Cluster2");
    private static final Region.Id REGION_1 = Region.Id.create("Region1");
    private static final Region.Id REGION_2 = Region.Id.create("Region2");
    private static final Country.Id COUNTRY_1 = Country.Id.create("Country1");

    private static final Member.Id MEMBER_1 = Member.Id.create(789L);
    private static final Member.Id MEMBER_2 = Member.Id.create(101L);

    private AdapterHelper() {
        // To prevent instantiation
    }

    public static GroupDTO groupDTO() {
        return GroupDtoFactory.convertGroup(group());
    }

    public static GroupsDTO groupsDTO() {
        return GroupDtoFactory.convertGroups(ImmutableSet.of(group()));
    }
}
