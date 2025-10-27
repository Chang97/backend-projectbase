package com.base.application.code.usecase.create;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.code.port.in.CreateCodeUseCase;
import com.base.application.code.port.out.CodePersistencePort;
import com.base.application.code.port.out.CodeReferencePort;
import com.base.application.code.usecase.command.CreateCodeCommand;
import com.base.application.code.usecase.query.assembler.CodeResultAssembler;
import com.base.application.code.usecase.result.CodeResult;
import com.base.domain.code.Code;
import com.base.exception.ConflictException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreateCodeUseCaseImpl implements CreateCodeUseCase {

    private final CodePersistencePort codePersistencePort;
    private final CodeReferencePort codeReferencePort;
    private final CodeResultAssembler codeResultAssembler;

    @Override
    public CodeResult handle(CreateCodeCommand command) {
        if (codePersistencePort.existsByCode(command.code())) {
            throw new ConflictException("Code already exists: " + command.code());
        }

        Code code = Code.builder()
                .code(command.code())
                .codeName(command.codeName())
                .description(command.description())
                .srt(command.srt())
                .etc1(command.etc1())
                .etc2(command.etc2())
                .etc3(command.etc3())
                .etc4(command.etc4())
                .build();
        code.setUseYn(command.useYn() == null ? Boolean.TRUE : command.useYn());
        applyUpperCode(code, command.upperCodeId());
        refreshOrderPath(code);

        Code saved = codePersistencePort.save(code);
        return codeResultAssembler.toResult(saved);
    }

    private void applyUpperCode(Code code, Long upperCodeId) {
        if (upperCodeId != null) {
            code.setUpperCode(codeReferencePort.getReference(upperCodeId));
        } else {
            code.setUpperCode(null);
        }
    }

    private void refreshOrderPath(Code code) {
        String segment = buildOrderSegment(code.getSrt(), code.getCode());
        Code parent = code.getUpperCode();
        if (parent != null) {
            String parentPath = parent.getOrderPath();
            code.setOrderPath(parentPath != null && !parentPath.isBlank() ? parentPath + ">" + segment : segment);
        } else {
            code.setOrderPath(segment);
        }
    }

    private String buildOrderSegment(Integer srt, String codeValue) {
        int order = srt != null ? srt : 999999;
        String padded = String.format("%06d", Math.min(order, 999999));
        return padded + ":" + (codeValue == null ? "" : codeValue);
    }
}
