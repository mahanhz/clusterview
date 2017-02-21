package org.amhzing.clusterview.backend.user;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.backend.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.backend.infra.jpa.mapping.user.RoleEntity;
import org.amhzing.clusterview.backend.infra.jpa.mapping.user.UserEntity;
import org.amhzing.clusterview.backend.infra.jpa.repository.user.UserJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserDetailsServiceTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private DefaultUserDetailsService defaultUserDetailsService;

    @Test
    public void should_return_user_details() {
        given(userJpaRepository.findByEmail(any())).willReturn(userEntity());

        final DefaultUserDetails userDetails = (DefaultUserDetails) defaultUserDetailsService.loadUserByUsername(username());

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(userEntity().getEmail());
        assertThat(userDetails.getFirstName()).isEqualTo(userEntity().getFirstName());
        assertThat(userDetails.getLastName()).isEqualTo(userEntity().getLastName());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void should_not_find_user() {
        defaultUserDetailsService.loadUserByUsername("hacker");

        fail("The user should not have been found");
    }

    private UserEntity userEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail(username());
        userEntity.setPassword("pass");
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEnabled(true);
        userEntity.setRoles(roles());
        userEntity.setCountries(ImmutableSet.of(JpaRepositoryHelper.countryEntity()));

        return userEntity;
    }

    private Set<RoleEntity> roles() {
        final RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName("ROLE_USER");

        return ImmutableSet.of(roleEntity);
    }

    private String username() {
        return "test@example.com";
    }
}