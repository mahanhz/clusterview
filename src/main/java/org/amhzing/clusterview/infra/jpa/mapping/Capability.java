package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;

@Entity
public class Capability {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Capability() {
    }

    private Capability(final Activity activity, final Member member) {
        this.activity = activity;
        this.member = member;
    }

    public static Capability create(final Activity activity, final Member member) {
        return new Capability(activity, member);
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
        return "Capability{" +
                "activity=" + activity +
                ", id=" + id +
                '}';
    }
}
