package org.amhzing.clusterview.app.user;

import org.amhzing.clusterview.app.infra.jpa.repository.user.UserJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.userEntity;
import static org.amhzing.clusterview.app.user.UserUtil.username;
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
}