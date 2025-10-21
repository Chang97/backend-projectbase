package com.base.application.menu.command;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.api.menu.dto.MenuRequest;
import com.base.api.menu.dto.MenuResponse;
import com.base.api.menu.mapper.MenuMapper;
import com.base.application.menu.query.MenuResponseAssembler;
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
public class MenuCommandServiceImpl implements MenuCommandService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final EntityManager entityManager;
    private final MenuPermissionMapRepository menuPermissionMapRepository;
    private final PermissionRepository permissionRepository;
    private final MenuResponseAssembler menuResponseAssembler;

    @Override
    @Transactional
    public MenuResponse createMenu(MenuRequest request) {
        if (menuRepository.existsByMenuCode(request.menuCode())) {
            throw new ConflictException("Menu code already exists: " + request.menuCode());
        }
        Menu menu = menuMapper.toEntity(request);
        applyUpperMenu(menu, request.upperMenuId());
        Menu saved = menuRepository.save(menu);
        syncMenuPermissions(saved, request.permissionIds());
        List<Long> permissionIds = menuPermissionMapRepository.findPermissionIdsByMenuId(saved.getMenuId());
        return menuResponseAssembler.assemble(saved, permissionIds);
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
        Menu saved = menuRepository.save(existing);
        syncMenuPermissions(saved, request.permissionIds());
        List<Long> permissionIds = menuPermissionMapRepository.findPermissionIdsByMenuId(saved.getMenuId());
        return menuResponseAssembler.assemble(saved, permissionIds);
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

    private void syncMenuPermissions(Menu menu, List<Long> permissionIds) {
        if (permissionIds == null) {
            return;
        }
        List<Long> sanitizedIds = permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        menuPermissionMapRepository.deleteByMenuMenuId(menu.getMenuId());

        if (sanitizedIds.isEmpty()) {
            return;
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
    }
}
