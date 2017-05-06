package org.amhzing.clusterview.jpa.entity;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity(name = "cluster")
public final class ClusterEntity extends BaseEntity {

    @Id
    private String id;

    private String areaCoords;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamEntity> teams;

    @ManyToOne(fetch = FetchType.LAZY)
    private RegionEntity region;

    @ElementCollection
    @CollectionTable(name = "clusters_courses", joinColumns = @JoinColumn(name = "cluster_id", referencedColumnName = "id"))
    @MapKeyJoinColumn(name = "course_id", referencedColumnName = "id")
    private Map<CourseEntity, Integer> courses;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getAreaCoords() {
        return areaCoords;
    }

    public void setAreaCoords(final String areaCoords) {
        this.areaCoords = areaCoords;
    }

    public Set<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(final Set<TeamEntity> teams) {
        this.teams = teams;
    }

    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(final RegionEntity region) {
        this.region = region;
    }

    public Map<CourseEntity, Integer> getCourses() {
        return courses;
    }

    public void setCourses(final Map<CourseEntity, Integer> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ClusterEntity)) return false;
        final ClusterEntity that = (ClusterEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(areaCoords, that.areaCoords) &&
                Objects.equals(teams, that.teams) &&
                Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, areaCoords, teams, courses);
    }

    @Override
    public String toString() {
        return "ClusterEntity{" +
                "id='" + id + '\'' +
                ", areaCoords='" + areaCoords + '\'' +
                ", teams=" + teams +
                ", courses=" + courses +
                "} " + super.toString();
    }
}
