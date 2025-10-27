package com.base.application.menu.usecase.create;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.application.menu.usecase.command.CreateMenuCommand;
import com.base.application.menu.usecase.query.assembler.MenuResultAssembler;
import com.base.application.menu.usecase.result.MenuResult;
import com.base.domain.mapping.MenuPermissionMap;
import com.base.domain.mapping.MenuPermissionMapRepository;
import com.base.domain.menu.Menu;
import com.base.domain.menu.MenuRepository;
import com.base.domain.permission.Permission;
import com.base.domain.permission.PermissionRepository;
import com.base.exception.ConflictException;
import com.base.exception.ValidationException;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreateMenuUseCaseImpl implements CreateMenuUseCase {

    private final MenuRepository menuRepository;
    private final EntityManager entityManager;
    private final MenuPermissionMapRepository menuPermissionMapRepository;
    private final PermissionRepository permissionRepository;
    private final MenuResultAssembler menuResultAssembler;

    @Override
    public MenuResult handle(CreateMenuCommand command) {
        if (menuRepository.existsByMenuCode(command.menuCode())) {
            throw new ConflictException("Menu code already exists: " + command.menuCode());
        }

        Menu menu = Menu.builder()
                .menuCode(command.menuCode())
                .menuName(command.menuName())
                .menuCn(command.menuCn())
                .url(command.url())
                .srt(command.srt())
                .build();
        menu.setUseYn(command.useYn() == null ? Boolean.TRUE : command.useYn());
        applyUpperMenu(menu, command.upperMenuId());

        Menu saved = menuRepository.save(menu);
        List<Long> permissionIds = syncMenuPermissions(saved, command.permissionIds());
        return menuResultAssembler.toResult(saved, permissionIds);
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
