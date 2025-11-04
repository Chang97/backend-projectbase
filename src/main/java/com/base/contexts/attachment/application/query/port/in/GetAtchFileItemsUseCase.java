package com.base.contexts.attachment.application.query.port.in;

import java.util.List;

import com.base.contexts.attachment.application.query.dto.AtchFileItemResult;

public interface GetAtchFileItemsUseCase {

    List<AtchFileItemResult> handle(Long atchFileId);
}
