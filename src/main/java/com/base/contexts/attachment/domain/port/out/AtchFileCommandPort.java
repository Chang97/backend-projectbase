package com.base.contexts.attachment.domain.port.out;

import java.util.Optional;

import com.base.contexts.attachment.domain.model.AtchFile;

public interface AtchFileCommandPort {

    AtchFile save(AtchFile atchFile);

    Optional<AtchFile> findById(Long atchFileId);

    void deleteById(Long atchFileId);
}
