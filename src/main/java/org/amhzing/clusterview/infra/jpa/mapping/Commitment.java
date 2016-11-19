package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;

@Entity
public class Commitment {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Commitment() {
    }

    private Commitment(final Activity activity, final Member member) {
        this.activity = activity;
        this.member = member;
    }

    public static Commitment create(final Activity activity, final Member member) {
        return new Commitment(activity, member);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(final Activity activity) {
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
        return "Commitment{" +
                "activity=" + activity +
                ", id=" + id +
                '}';
    }
}
