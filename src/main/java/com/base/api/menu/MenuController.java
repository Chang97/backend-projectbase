package com.base.api.menu;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.application.menu.command.MenuCommandService;
import com.base.application.menu.query.MenuQueryService;
import com.base.domain.menu.Menu;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    
    private final MenuCommandService menuCommandService;
    private final MenuQueryService menuQueryService;

    // 조회
    @GetMapping
    public List<Menu> getAllMenus() {
        return menuQueryService.getMenus();
    }

    @GetMapping("/{id}")
    public Menu getMenu(@PathVariable Long id) {
        return menuQueryService.getMenu(id);
    }

    // 생성
    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        return menuCommandService.createMenu(menu);
    }

    // 수정
    @PutMapping("/{id}")
    public Menu updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
        return menuCommandService.updateMenu(id, menu);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuCommandService.deleteMenu(id);
    }
}
