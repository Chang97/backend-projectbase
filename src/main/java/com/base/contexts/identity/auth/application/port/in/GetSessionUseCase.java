package com.base.contexts.identity.auth.application.port.in;

import com.base.contexts.identity.auth.application.dto.AuthSession;

public interface GetSessionUseCase {

    AuthSession handle();
}
