package org.amhzing.clusterview.user;

import org.amhzing.clusterview.infra.jpa.mapping.user.RoleEntity;
import org.amhzing.clusterview.infra.jpa.mapping.user.UserEntity;
import org.amhzing.clusterview.infra.jpa.repository.user.UserJpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

public class DefaultUserDetailsService implements UserDetailsService {

    private UserJpaRepository userJpaRepository;

    public DefaultUserDetailsService(final UserJpaRepository userJpaRepository) {
        this.userJpaRepository = notNull(userJpaRepository);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserEntity user = userJpaRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + user);
        }

        return new DefaultUserDetails(user.getEmail(),
                                      user.getPassword(),
                                      user.isEnabled(),
                                      true, true, true,
                                      authorities(user.getRoles()),
                                      user.getFirstName(),
                                      user.getLastName());
    }

    protected Collection<GrantedAuthority> authorities(final Collection<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(toList());
    }
}
