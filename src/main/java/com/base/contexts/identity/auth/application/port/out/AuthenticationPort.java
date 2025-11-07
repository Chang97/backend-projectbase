package com.base.contexts.identity.auth.application.port.out;

import com.base.platform.security.userdetails.UserPrincipal;

public interface AuthenticationPort {
    UserPrincipal authenticate(String loginId, String rawPassword);
}
