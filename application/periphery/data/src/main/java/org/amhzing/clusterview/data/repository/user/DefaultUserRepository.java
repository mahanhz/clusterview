package org.amhzing.clusterview.data.repository.user;

import org.amhzing.clusterview.core.boundary.exit.repository.UserRepository;
import org.amhzing.clusterview.core.domain.user.Page;
import org.amhzing.clusterview.core.domain.user.Password;
import org.amhzing.clusterview.core.domain.user.User;
import org.amhzing.clusterview.data.jpa.entity.user.UserEntity;
import org.amhzing.clusterview.data.jpa.repository.user.UserJpaRepository;
import org.amhzing.clusterview.infra.exception.NotFoundException;
import org.amhzing.clusterview.infra.user.UserUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

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
        final PageRequest pageRequest = new PageRequest(pageNumber, 2);

        final org.springframework.data.domain.Page<UserEntity> pagedEntity = userJpaRepository.findAll(pageRequest);

        final List<User> users = UserFactory.users(pagedEntity.getContent());

        return Page.create(users, pagedEntity.getTotalPages());
    }
}
