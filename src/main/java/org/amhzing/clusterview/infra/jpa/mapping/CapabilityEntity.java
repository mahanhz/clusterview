package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;

@Entity(name = "capability")
public class CapabilityEntity {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ActivityEntity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity member;

    public CapabilityEntity() {
    }

    private CapabilityEntity(final ActivityEntity activity, final MemberEntity member) {
        this.activity = activity;
        this.member = member;
    }

    public static CapabilityEntity create(final ActivityEntity activity, final MemberEntity member) {
        return new CapabilityEntity(activity, member);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public ActivityEntity getActivity() {
        return activity;
    }

    public void setActivity(final ActivityEntity activity) {
        this.activity = activity;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(final MemberEntity member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "CapabilityEntity{" +
                "activity=" + activity +
                ", id=" + id +
                '}';
    }
}
