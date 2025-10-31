package com.base.contexts.code.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.code.application.command.port.in.DeleteCodeUseCase;
import com.base.contexts.code.domain.model.Code;
import com.base.contexts.code.domain.port.out.CodeRepository;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteCodeHandler implements DeleteCodeUseCase {

    private final CodeRepository codeRepository;

    @Override
    public void handle(Long codeId) {
        Code existing = codeRepository.findById(codeId)
                .orElseThrow(() -> new NotFoundException("Code not found"));
        existing.disable();
        codeRepository.save(existing);
    }
}
