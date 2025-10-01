package com.base.application.atchFileItem.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.atchFileItem.dto.AtchFileItemResponse;
import com.base.api.atchFileItem.mapper.AtchFileItemMapper;
import com.base.domain.atchFileItem.AtchFileItemRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtchFileItemQueryServiceImpl implements AtchFileItemQueryService {

    private final AtchFileItemRepository entityVarRepository;
    private final AtchFileItemMapper entityVarMapper;

    @Override
    @Transactional(readOnly = true)
    public AtchFileItemResponse getAtchFileItem(Long id) {
        return entityVarMapper.toResponse(entityVarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AtchFileItem not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AtchFileItemResponse> getAtchFileItemList() {
        return entityVarRepository.findAll().stream()
                .map(entityVarMapper::toResponse)
                .toList();
    }
}