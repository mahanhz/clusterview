package org.amhzing.clusterview.data.jpa.entity.user;

import org.amhzing.clusterview.data.jpa.entity.BaseEntity;
import org.amhzing.clusterview.data.jpa.entity.CountryEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Entity(name = "userlogin")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    @Column(length = 60)
    private String password;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<RoleEntity> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_countries",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
    private Set<CountryEntity> countries;

    protected UserEntity() {
    }

    public UserEntity(final long id, final String firstName, final String lastName, final String email,
                      final String password, final boolean enabled, final Set<RoleEntity> roles,
                      final Set<CountryEntity> countries) {
        this.id = id;
        this.firstName = notBlank(firstName);
        this.lastName = notBlank(lastName);
        this.email = notBlank(email);
        this.password = notBlank(password);
        this.enabled = enabled;
        this.roles = notNull(roles);
        this.countries = notNull(countries);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(final Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public Set<CountryEntity> getCountries() {
        return countries;
    }

    public void setCountries(final Set<CountryEntity> countries) {
        this.countries = countries;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserEntity that = (UserEntity) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                "} " + super.toString();
    }
}
