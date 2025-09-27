package com.base.application.menu.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.domain.menu.Menu;
import com.base.domain.menu.MenuRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuQueryServiceImpl implements MenuQueryService {

    private final MenuRepository menuRepository;

    @Override
    public List<Menu> getMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getMenu(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu not found"));
    }
}
