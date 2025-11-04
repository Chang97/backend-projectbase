package com.base.contexts.attachment.application.query.port.in;

import com.base.contexts.attachment.application.query.dto.AtchFileQueryResult;

public interface GetAtchFileUseCase {

    AtchFileQueryResult handle(Long atchFileId);
}
