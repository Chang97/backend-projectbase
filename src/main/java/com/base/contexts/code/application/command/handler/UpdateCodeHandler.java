package com.base.contexts.code.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.code.application.command.dto.CodeCommand;
import com.base.contexts.code.application.command.dto.CodeCommandResult;
import com.base.contexts.code.application.command.mapper.CodeCommandMapper;
import com.base.contexts.code.application.command.port.in.UpdateCodeUseCase;
import com.base.contexts.code.domain.model.Code;
import com.base.contexts.code.domain.model.CodeId;
import com.base.contexts.code.domain.policy.CodePolicy;
import com.base.contexts.code.domain.port.out.CodeCommandPort;
import com.base.platform.exception.ConflictException;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateCodeHandler implements UpdateCodeUseCase {

    private final CodeCommandPort codeCommandPort;
    private final CodeCommandMapper codeCommandMapper;
    private final CodePolicy codePolicy;

    @Override
    public CodeCommandResult handle(Long codeId, CodeCommand command) {
        Code existing = codeCommandPort.findById(codeId)
                .orElseThrow(() -> new NotFoundException("Code not found"));

        if (!existing.getCode().equals(command.code())
                && codeCommandPort.existsByCode(command.code())) {
            throw new ConflictException("Code already exists: " + command.code());
        }

        Code code = codeCommandMapper.toDomain(codeId, command);
        CodeId upperId = code.getUpperCodeId();
        String parentOrderPath = codePolicy.resolveParentOrderPath(upperId);
        code.attachTo(upperId, parentOrderPath);

        Code saved = codeCommandPort.save(code);
        return codeCommandMapper.toCommandResult(saved);

    }

}
