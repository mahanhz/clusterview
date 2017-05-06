package org.amhzing.clusterview.jpa.entity;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "commitment")
public final class CommitmentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ActivityEntity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity member;

    public CommitmentEntity() {
    }

    private CommitmentEntity(final ActivityEntity activity, final MemberEntity member) {
        this.activity = activity;
        this.member = member;
    }

    public static CommitmentEntity create(final ActivityEntity activity, final MemberEntity member) {
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

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(final MemberEntity member) {
        this.member = member;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CommitmentEntity that = (CommitmentEntity) o;
        return id == that.id &&
                Objects.equals(activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activity);
    }

    @Override
    public String toString() {
        return "CommitmentEntity{" +
                "activity=" + activity +
                ", id=" + id +
                '}';
    }
}
