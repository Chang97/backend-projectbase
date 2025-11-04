package com.base.contexts.attachment.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.base.contexts.attachment.domain.model.AtchFile;

public interface AtchFileRepository {

    AtchFile save(AtchFile atchFile);

    Optional<AtchFile> findById(Long atchFileId);

    List<AtchFile> findAll();

    void deleteById(Long atchFileId);
}
