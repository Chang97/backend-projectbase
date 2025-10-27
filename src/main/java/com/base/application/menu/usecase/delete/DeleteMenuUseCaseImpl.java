package com.base.application.menu.usecase.delete;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.domain.menu.Menu;
import com.base.domain.menu.MenuRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteMenuUseCaseImpl implements DeleteMenuUseCase {

    private final MenuRepository menuRepository;

    @Override
    public void handle(Long menuId) {
        Menu existing = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu not found"));
        existing.setUseYn(false);
        menuRepository.save(existing);
    }
}
