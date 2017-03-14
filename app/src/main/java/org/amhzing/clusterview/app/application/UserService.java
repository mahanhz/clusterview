package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.user.Password;

public interface UserService {

    void changePassword(final Password password);
}
