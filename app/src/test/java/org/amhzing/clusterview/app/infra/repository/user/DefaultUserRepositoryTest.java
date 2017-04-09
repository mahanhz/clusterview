package org.amhzing.clusterview.app.infra.repository.user;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.domain.model.user.ImmutablePassword;
import org.amhzing.clusterview.app.domain.model.user.Page;
import org.amhzing.clusterview.app.domain.model.user.Password;
import org.amhzing.clusterview.app.infra.jpa.repository.user.UserJpaRepository;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.userEntity;
import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void should_get_users() throws Exception {

        given(userJpaRepository.findAll(any(PageRequest.class))).willReturn(new PageImpl(ImmutableList.of(userEntity())));

        final Page<org.amhzing.clusterview.app.domain.model.user.User> users = defaultUserRepository.users(1);

        assertThat(users.getTotalPages()).isGreaterThan(0);
        assertThat(users.getContent()).hasSize(1);
        assertThat(users.getContent().get(0)).has(email(userEntity().getEmail()));
    }

    private Condition email(final String email) {
        return new Condition<org.amhzing.clusterview.app.domain.model.user.User>("email") {
            @Override
            public boolean matches(final org.amhzing.clusterview.app.domain.model.user.User user) {
                return user.getEmail().value().equalsIgnoreCase(email);
            }
        };
    }
}