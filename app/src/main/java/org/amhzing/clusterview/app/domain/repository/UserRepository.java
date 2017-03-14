package org.amhzing.clusterview.app.domain.repository;

import org.amhzing.clusterview.app.domain.model.user.Password;

public interface UserRepository {

    void changePassword(final Password password);
}
