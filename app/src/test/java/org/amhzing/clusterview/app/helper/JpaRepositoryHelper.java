package org.amhzing.clusterview.app.helper;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import org.amhzing.clusterview.app.infra.jpa.mapping.*;
import org.amhzing.clusterview.app.infra.jpa.mapping.stats.ActivityStats;
import org.amhzing.clusterview.app.infra.jpa.mapping.stats.CoreActivityStats;
import org.amhzing.clusterview.app.infra.jpa.mapping.stats.StatsHistoryEntity;
import org.amhzing.clusterview.app.infra.jpa.mapping.stats.StatsHistoryPk;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public final class JpaRepositoryHelper {

    public static final int INITIAL_ACTIVITIES_SIZE = 10;
    public static final int INITIAL_CLUSTERS_SIZE = 19;
    public static final int INITIAL_TEAM_SIZE = 3;
    public static final int INITIAL_MEMBERS_SIZE = 4;
    public static final int INITIAL_CAPABILITIES_SIZE = 6;
    public static final int INITIAL_COMMITMENTS_SIZE = 8;
    public static final int INITIAL_STATS_HISTORY_STOCKHOLM_SIZE = 3;


    private JpaRepositoryHelper() {
        // To prevent instantiation
    }

    public static StatsHistoryEntity statsHistoryEntity() {
        final StatsHistoryEntity statsHistoryEntity = new StatsHistoryEntity();

        statsHistoryEntity.setStatsHistoryPk(StatsHistoryPk.create("stockholm", new Date()));
        statsHistoryEntity.setCc(coreActivityStats());
        statsHistoryEntity.setDm(coreActivityStats());
        statsHistoryEntity.setJyg(coreActivityStats());
        statsHistoryEntity.setSc(coreActivityStats());
        statsHistoryEntity.setActivityStats(activityStats());

        return statsHistoryEntity;
    }

    public static CountryEntity countryEntity() {
        final CountryEntity country = new CountryEntity();
        country.setId("se");
        country.setRegions(ImmutableSet.of(regionEntity()));

        return country;
    }

    public static RegionEntity regionEntity() {
        final RegionEntity region = new RegionEntity();
        region.setId("central");
        region.setClusters(ImmutableSet.of(clusterEntity()));
        //region.setCountry(countryEntity());

        return region;
    }

    public static ClusterEntity clusterEntity() {
        final ClusterEntity cluster = new ClusterEntity();
        cluster.setId("stockholm");
        cluster.setTeams(ImmutableSet.of(teamEntity()));
        cluster.setCourses(courses());
        //cluster.setRegion(region());

        return cluster;
    }

    public static ClusterEntity anotherClusterEntity() {
        final ClusterEntity cluster = new ClusterEntity();
        cluster.setId("uppsala");
        cluster.setTeams(ImmutableSet.of(teamEntity()));
        cluster.setCourses(courses());
        //cluster.setRegion(region());

        return cluster;
    }

    public static TeamEntity teamEntity() {
        final TeamEntity team = new TeamEntity();
        team.setLocation(location());

        final HashSet<MemberEntity> memberEntities = new HashSet<>();
        memberEntities.add(member());
        team.setMembers(memberEntities);

        team.setCoreActivities(coreActivityQuantity());
        //teamEntity.setCluster(clusterEntity());

        return team;
    }

    public static Location location() {
        final Location location = new Location();
        location.setX(1);
        location.setY(1);

        return location;
    }

    public static Map<CoreActivityEntity, ParticipantQuantity> coreActivityQuantity() {
        final HashMap<CoreActivityEntity, ParticipantQuantity> map = Maps.newHashMap();

         map.put(coreActivityEntity(), participantQuantity());

        return map;
    }

    public static CoreActivityEntity coreActivityEntity() {
        final CoreActivityEntity coreActivityEntity = new CoreActivityEntity();
        coreActivityEntity.setId("sc");
        coreActivityEntity.setName("SC");

        return coreActivityEntity;
    }

    public static ParticipantQuantity participantQuantity() {
        return ParticipantQuantity.create(3, 10, 5);
    }

    public static MemberEntity member() {
        final MemberEntity member = new MemberEntity();
        member.setName(name());
        member.setCapabilities(capabilities(member));
        member.setCommitments(commitments(member));
        //member.setTeam(teamEntity());

        return member;
    }

    public static ImmutableSet<CapabilityEntity> capabilities(final MemberEntity member) {
        return ImmutableSet.of(capability(member));
    }

    public static CapabilityEntity capability(final MemberEntity member) {
        return CapabilityEntity.create(newActivity(), member);
    }

    public static ImmutableSet<CommitmentEntity> commitments(final MemberEntity member) {
        return ImmutableSet.of(CommitmentEntity.create(anotherNewActivity(), member));
    }

    public static ActivityEntity newActivity() {
        final ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setId("NO");
        activityEntity.setName("Nonsense");

        return activityEntity;
    }

    public static ActivityEntity anotherNewActivity() {
        final ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setId("YE");
        activityEntity.setName("Something");

        return activityEntity;
    }

    public static CourseEntity courseEntity(final String id, final String name) {
        final CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(id);
        courseEntity.setName(name);

        return courseEntity;
    }

    public static Map<CourseEntity, Integer> courses() {
        final Map<CourseEntity, Integer> courses = new HashMap<>();
        courses.put(courseEntity("1", "Book 1"), 50);
        courses.put(courseEntity("2", "Book 2"), 40);
        courses.put(courseEntity("3", "Book 3"), 30);
        courses.put(courseEntity("4", "Book 4"), 20);
        courses.put(courseEntity("5", "Book 5"), 10);
        courses.put(courseEntity("6", "Book 6"), 9);
        courses.put(courseEntity("7", "Book 7"), 8);
        courses.put(courseEntity("8", "Book 8"), 7);
        courses.put(courseEntity("10", "Book 10"), 6);

        return courses;
    }

    public static Name name() {
        return Name.create("Joanne", null, "Smith", null);
    }

    public static Name nameFor111() {
        return Name.create("John", "M", "Doe", "I");
    }

    private static CoreActivityStats coreActivityStats() {
        return CoreActivityStats.create(1, 10, 5);
    }

    private static ActivityStats activityStats() {
        return ActivityStats.create(1, 2, 3, 4, 5);
    }
}
