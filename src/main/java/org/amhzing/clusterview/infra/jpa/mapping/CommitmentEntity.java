package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;

@Entity
public class CommitmentEntity {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ActivityEntity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public CommitmentEntity() {
    }

    private CommitmentEntity(final ActivityEntity activity, final Member member) {
        this.activity = activity;
        this.member = member;
    }

    public static CommitmentEntity create(final ActivityEntity activity, final Member member) {
        return new CommitmentEntity(activity, member);
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

    public Member getMember() {
        return member;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "CommitmentEntity{" +
                "activity=" + activity +
                ", id=" + id +
                '}';
    }
}
