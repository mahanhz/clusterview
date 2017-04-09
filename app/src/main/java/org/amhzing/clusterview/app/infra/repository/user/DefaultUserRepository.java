package org.amhzing.clusterview.app.infra.repository.user;

import org.amhzing.clusterview.app.domain.model.user.Password;
import org.amhzing.clusterview.app.domain.model.user.User;
import org.amhzing.clusterview.app.domain.repository.UserRepository;
import org.amhzing.clusterview.app.exception.NotFoundException;
import org.amhzing.clusterview.app.infra.jpa.mapping.user.UserEntity;
import org.amhzing.clusterview.app.infra.jpa.repository.user.UserJpaRepository;
import org.amhzing.clusterview.app.user.UserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultUserRepository implements UserRepository {

    private UserJpaRepository userJpaRepository;
    private PasswordEncoder passwordEncoder;

    public DefaultUserRepository(final UserJpaRepository userJpaRepository,
                                 final PasswordEncoder passwordEncoder) {
        this.userJpaRepository = notNull(userJpaRepository);
        this.passwordEncoder = notNull(passwordEncoder);
    }

    @Override
    public void changePassword(final Password password) {
        notNull(password);

        final UserEntity user = userJpaRepository.findByEmail(UserUtil.username());

        if (user == null) {
            throw new NotFoundException("Could not save password as user " + UserUtil.username() + " does not exist");
        }

        user.setPassword(passwordEncoder.encode(password.value()));

        userJpaRepository.save(user);
    }

    @Override
    public Page<User> users(final int pageNumber) {
        final PageRequest pageRequest = new PageRequest(pageNumber, 5);

        final Page<UserEntity> pagedEntity = userJpaRepository.findAll(pageRequest);

        return null;
    }
}
