package org.amhzing.clusterview.web.adapter;

import org.springframework.stereotype.Service;

import static org.amhzing.clusterview.user.UserUtil.hasRole;

@Service
public class UIAccessService {

    public boolean includeGroupOperation() {
        return hasRole("ROLE_SE_ADMIN");
    }
}
