package org.amhzing.clusterview.app.domain.repository;

import org.amhzing.clusterview.app.domain.model.user.Password;
import org.amhzing.clusterview.app.domain.model.user.User;
import org.springframework.data.domain.Page;

public interface UserRepository {

    void changePassword(final Password password);

    // FIXME - Prefer domain to be framework agnostic
    Page<User> users(final int pageNumber);
}
