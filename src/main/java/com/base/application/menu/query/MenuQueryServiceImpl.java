package com.base.application.menu.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.menu.dto.MenuResponse;
import com.base.api.menu.mapper.MenuMapper;
import com.base.domain.menu.MenuRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuQueryServiceImpl implements MenuQueryService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getMenus() {
        return menuMapper.toResponseList(menuRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public MenuResponse getMenu(Long id) {
        return menuMapper.toResponse(menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getMenusByUpperId(Long upperMenuId) {
        return menuMapper.toResponseList(
                menuRepository.findByUpperMenu_MenuIdAndUseYnTrueOrderBySrtAsc(upperMenuId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getMenusByUpperMenu(String upperMenu) {
        return menuMapper.toResponseList(
                menuRepository.findByUpperMenu_MenuAndUseYnTrueOrderBySrtAsc(upperMenu)
        );
    }
}
