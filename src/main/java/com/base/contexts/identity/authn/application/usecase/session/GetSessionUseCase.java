package com.base.contexts.identity.authn.application.usecase.session;

import com.base.contexts.identity.authn.application.usecase.result.AuthSession;

public interface GetSessionUseCase {

    AuthSession handle();
}
