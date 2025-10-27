package com.base.application.code.usecase.delete;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.code.port.in.DeleteCodeUseCase;
import com.base.application.code.port.out.CodePersistencePort;
import com.base.domain.code.Code;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteCodeUseCaseImpl implements DeleteCodeUseCase {

    private final CodePersistencePort codePersistencePort;

    @Override
    public void handle(Long codeId) {
        Code existing = codePersistencePort.findById(codeId)
                .orElseThrow(() -> new NotFoundException("Code not found"));
        existing.setUseYn(false);
        codePersistencePort.save(existing);
    }
}
