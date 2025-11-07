package com.base.contexts.code.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.code.application.command.port.in.DeleteCodeUseCase;
import com.base.contexts.code.domain.model.Code;
import com.base.contexts.code.domain.port.out.CodeCommandPort;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteCodeHandler implements DeleteCodeUseCase {

    private final CodeCommandPort codeCommandPort;

    @Override
    public void handle(Long codeId) {
        Code existing = codeCommandPort.findById(codeId)
                .orElseThrow(() -> new NotFoundException("Code not found"));
        existing.disable();
        codeCommandPort.save(existing);
    }
}
