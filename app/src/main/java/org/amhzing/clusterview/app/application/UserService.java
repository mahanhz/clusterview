package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.user.Page;
import org.amhzing.clusterview.app.domain.model.user.Password;
import org.amhzing.clusterview.app.domain.model.user.User;

public interface UserService {

    void changePassword(final Password password);

    Page<User> users(final int pageNumber);
}
