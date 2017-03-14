package org.amhzing.clusterview.app.infra.repository.user;

import org.amhzing.clusterview.app.domain.model.user.ImmutablePassword;
import org.amhzing.clusterview.app.domain.model.user.Password;
import org.amhzing.clusterview.app.infra.jpa.repository.user.UserJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.userEntity;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserRepositoryTest {

    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private DefaultUserRepository defaultUserRepository;

    @Before
    public void setUp() throws Exception {
        defaultUserRepository = new DefaultUserRepository(userJpaRepository, passwordEncoder);

        final User user = new User("me@example.com", "Nopass" , emptyList());
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void should_change_password() throws Exception {

        given(userJpaRepository.findByEmail(any())).willReturn(userEntity());

        final Password password = ImmutablePassword.of("MyNewPass123");
        defaultUserRepository.changePassword(password);

        verify(userJpaRepository, times(1)).save(userEntity());
    }
}