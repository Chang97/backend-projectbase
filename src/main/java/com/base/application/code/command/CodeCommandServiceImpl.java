package com.base.application.code.command;

import org.springframework.stereotype.Service;

import com.base.domain.code.Code;
import com.base.domain.code.CodeRepository;
import com.base.exception.BusinessException;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CodeCommandServiceImpl implements CodeCommandService {

    private final CodeRepository codeRepository;

    @Override
    public Code createCode(Code code) {
        return codeRepository.save(code);
    }

    @Override
    public Code updateCode(Long id, Code code) {
        Code existing = codeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Code not found"));
        existing.setCode(code.getCode());
        existing.setCodeName(code.getCodeName());
        existing.setDescription(code.getDescription());
        existing.setSrt(code.getSrt());
        existing.setEtc1(code.getEtc1());
        existing.setEtc2(code.getEtc2());
        existing.setEtc3(code.getEtc3());
        existing.setEtc4(code.getEtc4());
        existing.setUseYn(code.getUseYn());
        return codeRepository.save(existing);
    }

    @Override
    public void deleteCode(Long id) {
        codeRepository.deleteById(id);
    }

    
}
