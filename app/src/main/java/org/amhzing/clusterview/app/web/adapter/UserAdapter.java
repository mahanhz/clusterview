package org.amhzing.clusterview.app.web.adapter;

import org.amhzing.clusterview.app.application.UserService;
import org.amhzing.clusterview.app.domain.model.user.ImmutablePassword;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class UserAdapter {

    private UserService userService;

    public UserAdapter(final UserService userService) {
        this.userService = notNull(userService);
    }

    public void changePassword(final String password) {
        notBlank(password);

        userService.changePassword(ImmutablePassword.of(password));
    }
}