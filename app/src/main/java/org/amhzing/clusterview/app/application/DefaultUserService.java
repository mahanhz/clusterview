package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.user.Page;
import org.amhzing.clusterview.app.domain.model.user.Password;
import org.amhzing.clusterview.app.domain.model.user.User;
import org.amhzing.clusterview.app.domain.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultUserService implements UserService {

    private UserRepository userRepository;

    public DefaultUserService(final UserRepository userRepository) {
        this.userRepository = notNull(userRepository);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void changePassword(final Password password) {
        notNull(password);

        userRepository.changePassword(password);
    }

    @Override
    public Page<User> users(final int pageNumber) {
        return userRepository.users(pageNumber);
    }
}
