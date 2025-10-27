package com.base.application.code.usecase.update;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.code.port.in.UpdateCodeUseCase;
import com.base.application.code.port.out.CodePersistencePort;
import com.base.application.code.port.out.CodeReferencePort;
import com.base.application.code.usecase.command.UpdateCodeCommand;
import com.base.application.code.usecase.query.assembler.CodeResultAssembler;
import com.base.application.code.usecase.result.CodeResult;
import com.base.domain.code.Code;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateCodeUseCaseImpl implements UpdateCodeUseCase {

    private final CodePersistencePort codePersistencePort;
    private final CodeReferencePort codeReferencePort;
    private final CodeResultAssembler codeResultAssembler;

    @Override
    public CodeResult handle(Long codeId, UpdateCodeCommand command) {
        Code existing = codePersistencePort.findById(codeId)
                .orElseThrow(() -> new NotFoundException("Code not found"));

        if (!existing.getCode().equals(command.code())
                && codePersistencePort.existsByCode(command.code())) {
            throw new ConflictException("Code already exists: " + command.code());
        }

        existing.setCode(command.code());
        existing.setCodeName(command.codeName());
        existing.setDescription(command.description());
        existing.setSrt(command.srt());
        existing.setEtc1(command.etc1());
        existing.setEtc2(command.etc2());
        existing.setEtc3(command.etc3());
        existing.setEtc4(command.etc4());
        existing.setUseYn(command.useYn() == null ? existing.getUseYn() : command.useYn());

        applyUpperCode(existing, command.upperCodeId());
        refreshOrderPathRecursively(existing);

        return codeResultAssembler.toResult(existing);
    }

    private void applyUpperCode(Code code, Long upperCodeId) {
        if (upperCodeId != null) {
            code.setUpperCode(codeReferencePort.getReference(upperCodeId));
        } else {
            code.setUpperCode(null);
        }
    }

    private void refreshOrderPathRecursively(Code code) {
        refreshOrderPath(code);
        if (code.getCodeId() == null) {
            return;
        }
        List<Code> children = codePersistencePort.findChildrenByUpperId(code.getCodeId());
        for (Code child : children) {
            refreshOrderPathRecursively(child);
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
