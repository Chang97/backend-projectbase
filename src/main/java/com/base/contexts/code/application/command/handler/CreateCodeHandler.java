package com.base.contexts.code.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.code.application.command.dto.CodeCommand;
import com.base.contexts.code.application.command.dto.CodeCommandResult;
import com.base.contexts.code.application.command.mapper.CodeCommandMapper;
import com.base.contexts.code.application.command.port.in.CreateCodeUseCase;
import com.base.contexts.code.domain.model.Code;
import com.base.contexts.code.domain.port.out.CodeRepository;
import com.base.platform.exception.ConflictException;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreateCodeHandler implements CreateCodeUseCase {

    private final CodeRepository codeRepository;
    private final CodeCommandMapper codeCommandMapper;
    // private final CodeReferencePort codeReferencePort;
    // private final CodeResultAssembler codeResultAssembler;

    @Override
    public CodeCommandResult handle(CodeCommand command) {
        if (codeRepository.existsByCode(command.code())) {
            throw new ConflictException("Code already exists: " + command.code());
        }

        Code code = codeCommandMapper.toDomain(command);
        
        if (command.upperCodeId() != null) {
            Code parent = codeRepository.findById(command.upperCodeId())
                    .orElseThrow(() -> new NotFoundException("상위 코드가 없습니다. id=" + command.upperCodeId()));
            code.attachTo(parent);
        } else {
            code.attachTo(null);
        }

        Code saved = codeRepository.save(code);
        return codeCommandMapper.toCommandResult(saved);
    }
}
