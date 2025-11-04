package com.base.contexts.attachment.application.query.port.in;

import java.util.List;

import com.base.contexts.attachment.application.query.dto.AtchFileQuery;
import com.base.contexts.attachment.application.query.dto.AtchFileSummaryResult;

public interface GetAtchFilesUseCase {

    List<AtchFileSummaryResult> handle(AtchFileQuery query);
}
