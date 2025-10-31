package com.base.contexts.code.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.contexts.code.application.command.dto.CodeCommand;
import com.base.contexts.code.application.command.dto.CodeCommandResult;
import com.base.contexts.code.application.command.mapper.CodeCommandMapper;
import com.base.contexts.code.application.command.port.in.UpdateCodeUseCase;
import com.base.contexts.code.domain.model.Code;
import com.base.contexts.code.domain.port.out.CodeRepository;
import com.base.platform.exception.ConflictException;
import com.base.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateCodeHandler implements UpdateCodeUseCase {

    private final CodeRepository codeRepository;
    private final CodeCommandMapper codeCommandMapper;

    @Override
    public CodeCommandResult handle(Long codeId, CodeCommand command) {
        Code existing = codeRepository.findById(codeId)
                .orElseThrow(() -> new NotFoundException("Code not found"));

        if (!existing.getCode().equals(command.code())
                && codeRepository.existsByCode(command.code())) {
            throw new ConflictException("Code already exists: " + command.code());
        }

        Code code = codeCommandMapper.toDomain(codeId, command);
        
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
