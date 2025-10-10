package com.base.application.menu.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.menu.dto.MenuRequest;
import com.base.api.menu.dto.MenuResponse;
import com.base.api.menu.mapper.MenuMapper;
import com.base.domain.menu.Menu;
import com.base.domain.menu.MenuRepository;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MenuCommandServiceImpl implements MenuCommandService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public MenuResponse createMenu(MenuRequest request) {
        if (menuRepository.existsByMenuCode(request.menuCode())) {
            throw new ConflictException("Menu code already exists: " + request.menuCode());
        }
        Menu menu = menuMapper.toEntity(request);
        applyUpperMenu(menu, request.upperMenuId());
        return menuMapper.toResponse(menuRepository.save(menu));
    }

    @Override
    @Transactional
    public MenuResponse updateMenu(Long id, MenuRequest request) {
        Menu existing = menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu not found"));

        if (!existing.getMenuCode().equals(request.menuCode())
                && menuRepository.existsByMenuCode(request.menuCode())) {
            throw new ConflictException("Menu code already exists: " + request.menuCode());
        }

        menuMapper.updateFromRequest(request, existing);
        applyUpperMenu(existing, request.upperMenuId());
        return menuMapper.toResponse(menuRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteMenu(Long id) {
        Menu existing = menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu not found"));
        existing.setUseYn(false); // soft delete
        menuRepository.save(existing);
    }

    private void applyUpperMenu(Menu menu, Long upperMenuId) {
        if (upperMenuId != null) {
            menu.setUpperMenu(entityManager.getReference(Menu.class, upperMenuId));
        } else {
            menu.setUpperMenu(null);
        }
    }

}

