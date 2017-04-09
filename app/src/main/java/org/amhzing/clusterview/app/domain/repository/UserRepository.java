package org.amhzing.clusterview.app.domain.repository;

import org.amhzing.clusterview.app.domain.model.user.Page;
import org.amhzing.clusterview.app.domain.model.user.Password;
import org.amhzing.clusterview.app.domain.model.user.User;

public interface UserRepository {

    void changePassword(final Password password);

    Page<User> users(final int pageNumber);
}
