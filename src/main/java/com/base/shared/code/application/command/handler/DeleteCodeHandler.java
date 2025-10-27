package com.base.shared.code.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.base.exception.NotFoundException;
import com.base.shared.code.application.command.port.in.DeleteCodeUseCase;
import com.base.shared.code.domain.model.Code;
import com.base.shared.code.domain.port.out.CodeRepository;

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
