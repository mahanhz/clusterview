package org.amhzing.clusterview.boundary.enter;

import org.amhzing.clusterview.domain.user.Page;
import org.amhzing.clusterview.domain.user.Password;
import org.amhzing.clusterview.domain.user.User;

public interface UserService {

    void changePassword(final Password password);

    Page<User> users(final int pageNumber);
}
