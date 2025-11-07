package com.base.contexts.attachment.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.base.contexts.attachment.domain.model.AtchFileItem;

public interface AtchFileItemQueryPort {

    Optional<AtchFileItem> findById(Long atchFileItemId);

    List<AtchFileItem> findByAtchFileId(Long atchFileId);
}
