package com.base.application.menu.command;

import org.springframework.stereotype.Service;

import com.base.domain.menu.Menu;
import com.base.domain.menu.MenuRepository;
import com.base.exception.NotFoundException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MenuCommandServiceImpl implements MenuCommandService {

    private final MenuRepository menuRepository;

    @Override
    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu updateMenu(Long id, Menu menu) {
        Menu existing = menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu not found"));
        existing.setUpperMenu(menu.getUpperMenu());
        existing.setMenuName(menu.getMenuName());
        existing.setSrt(menu.getSrt());
        existing.setUseYn(menu.getUseYn());
        return menuRepository.save(existing);
    }

    @Override
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    
}
