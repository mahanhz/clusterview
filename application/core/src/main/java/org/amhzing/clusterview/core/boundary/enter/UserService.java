package org.amhzing.clusterview.core.boundary.enter;

import org.amhzing.clusterview.core.domain.user.Page;
import org.amhzing.clusterview.core.domain.user.Password;
import org.amhzing.clusterview.core.domain.user.User;

public interface UserService {

    void changePassword(final Password password);

    Page<User> users(final int pageNumber);
}
