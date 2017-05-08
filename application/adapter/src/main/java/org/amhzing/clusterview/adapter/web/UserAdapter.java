package org.amhzing.clusterview.adapter.web;

import org.amhzing.clusterview.adapter.web.api.user.UsersDTO;
import org.amhzing.clusterview.adapter.web.util.UserDtoFactory;
import org.amhzing.clusterview.core.boundary.enter.UserService;
import org.amhzing.clusterview.core.domain.user.Page;
import org.amhzing.clusterview.core.domain.user.User;

import static org.apache.commons.lang3.Validate.notNull;

public class UserAdapter {

    private UserService userService;

    public UserAdapter(final UserService userService) {
        this.userService = notNull(userService);
    }

    public Page<User> pagedUsers(final int pageNumber) {

        return userService.users(pageNumber - 1);
    }

    public UsersDTO users(final int pageNumber) {

        return UserDtoFactory.users(pagedUsers(pageNumber).getContent());
    }
}
