package org.amhzing.clusterview.data.repository.user;

import org.amhzing.clusterview.data.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.data.jpa.repository.user.UserJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.infra.user.UserUtil.username;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserDetailsServiceTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    private DefaultUserDetailsService defaultUserDetailsService;

    @Before
    public void setUp() throws Exception {
        defaultUserDetailsService = new DefaultUserDetailsService(userJpaRepository);

        final User user = new User("me@example.com", "Nopass" , emptyList());
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void should_return_user_details() {
        given(userJpaRepository.findByEmail(ArgumentMatchers.any())).willReturn(JpaRepositoryHelper.userEntity());

        final DefaultUserDetails userDetails = (DefaultUserDetails) defaultUserDetailsService.loadUserByUsername(username());

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(JpaRepositoryHelper.userEntity().getEmail());
        assertThat(userDetails.getFirstName()).isEqualTo(JpaRepositoryHelper.userEntity().getFirstName());
        assertThat(userDetails.getLastName()).isEqualTo(JpaRepositoryHelper.userEntity().getLastName());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void should_not_find_user() {
        defaultUserDetailsService.loadUserByUsername("hacker");

        fail("The user should not have been found");
    }
}