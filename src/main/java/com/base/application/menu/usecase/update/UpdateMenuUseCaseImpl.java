package com.base.application.menu.usecase.update;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.menu.usecase.command.UpdateMenuCommand;
import com.base.application.menu.usecase.query.assembler.MenuResultAssembler;
import com.base.application.menu.usecase.result.MenuResult;
import com.base.domain.mapping.MenuPermissionMap;
import com.base.domain.mapping.MenuPermissionMapRepository;
import com.base.domain.menu.Menu;
import com.base.domain.menu.MenuRepository;
import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;
import com.base.exception.ConflictException;
import com.base.exception.NotFoundException;
import com.base.exception.ValidationException;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateMenuUseCaseImpl implements UpdateMenuUseCase {

    private final MenuRepository menuRepository;
    private final EntityManager entityManager;
    private final MenuPermissionMapRepository menuPermissionMapRepository;
    private final PermissionRepository permissionRepository;
    private final MenuResultAssembler menuResultAssembler;

    @Override
    public MenuResult handle(Long menuId, UpdateMenuCommand command) {
        Menu existing = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu not found"));

        if (command.menuCode() != null
                && !command.menuCode().equals(existing.getMenuCode())
                && menuRepository.existsByMenuCode(command.menuCode())) {
            throw new ConflictException("Menu code already exists: " + command.menuCode());
        }

        applyUpdates(existing, command);
        applyUpperMenu(existing, command.upperMenuId());

        Menu saved = menuRepository.save(existing);
        List<Long> permissionIds = syncMenuPermissions(saved, command.permissionIds());
        return menuResultAssembler.toResult(saved, permissionIds);
    }

    private void applyUpdates(Menu menu, UpdateMenuCommand command) {
        if (command.menuCode() != null) {
            menu.setMenuCode(command.menuCode());
        }
        if (command.menuName() != null) {
            menu.setMenuName(command.menuName());
        }
        if (command.menuCn() != null) {
            menu.setMenuCn(command.menuCn());
        }
        if (command.url() != null) {
            menu.setUrl(command.url());
        }
        if (command.srt() != null) {
            menu.setSrt(command.srt());
        }
        if (command.useYn() != null) {
            menu.setUseYn(command.useYn());
        }
    }

    private void applyUpperMenu(Menu menu, Long upperMenuId) {
        if (upperMenuId != null) {
            menu.setUpperMenu(entityManager.getReference(Menu.class, upperMenuId));
        } else {
            menu.setUpperMenu(null);
        }
    }

    private List<Long> syncMenuPermissions(Menu menu, List<Long> permissionIds) {
        if (permissionIds == null) {
            return menuPermissionMapRepository.findPermissionIdsByMenuId(menu.getMenuId());
        }

        List<Long> sanitizedIds = permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        menuPermissionMapRepository.deleteByMenuMenuId(menu.getMenuId());

        if (sanitizedIds.isEmpty()) {
            return List.of();
        }

        Map<Long, Permission> permissionMap = permissionRepository.findAllById(sanitizedIds).stream()
                .collect(Collectors.toMap(Permission::getPermissionId, Function.identity()));

        if (permissionMap.size() != sanitizedIds.size()) {
            throw new ValidationException("존재하지 않는 권한이 포함되어 있습니다.");
        }

        menuPermissionMapRepository.saveAll(
                sanitizedIds.stream()
                        .map(id -> MenuPermissionMap.builder()
                                .menu(menu)
                                .permission(permissionMap.get(id))
                                .build())
                        .toList()
        );
        return sanitizedIds;
    }
}
