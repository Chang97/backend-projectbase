package com.base.contexts.attachment.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.base.contexts.attachment.domain.model.AtchFileItem;

public interface AtchFileItemRepository {

    AtchFileItem save(AtchFileItem item);

    Optional<AtchFileItem> findById(Long atchFileItemId);

    List<AtchFileItem> findByAtchFileId(Long atchFileId);

    void deleteById(Long atchFileItemId);

    void deleteByAtchFileId(Long atchFileId);
}
