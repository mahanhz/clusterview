package org.amhzing.clusterview.core.usecase;

import org.amhzing.clusterview.core.boundary.enter.UserService;
import org.amhzing.clusterview.core.boundary.exit.repository.UserRepository;
import org.amhzing.clusterview.core.domain.user.Page;
import org.amhzing.clusterview.core.domain.user.Password;
import org.amhzing.clusterview.core.domain.user.User;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultUserService implements UserService {

    private UserRepository userRepository;

    public DefaultUserService(final UserRepository userRepository) {
        this.userRepository = notNull(userRepository);
    }

    @Override
    public void changePassword(final Password password) {
        notNull(password);

        userRepository.changePassword(password);
    }

    @Override
    public Page<User> users(final int pageNumber) {
        return userRepository.users(pageNumber);
    }
}
