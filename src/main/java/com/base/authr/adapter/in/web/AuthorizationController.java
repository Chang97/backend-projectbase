package com.base.authr.adapter.in.web;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.authr.adapter.in.web.dto.UserMenuAccessResponse;
import com.base.authr.adapter.in.web.mapper.AuthorizationResponseMapper;
import com.base.authr.application.dto.UserMenuAccessResult;
import com.base.authr.application.port.in.GetResolveMenusUseCase;

@RestController
@RequestMapping("/api/authr")
@Validated
@RequiredArgsConstructor
public class AuthorizationController {

    private final GetResolveMenusUseCase getResolveMenusUseCase;
    private final AuthorizationResponseMapper authorizationResponseMapper;

    @GetMapping("/resolveMenus/{userId}")
    @PreAuthorize("hasAuthority('MENU_LIST')")
    public ResponseEntity<UserMenuAccessResponse> getResolveMenus(@PathVariable Long userId) {
        UserMenuAccessResult result = getResolveMenusUseCase.handle(userId);
        UserMenuAccessResponse response = authorizationResponseMapper.toUserMenuAccessResponse(result); 
        return ResponseEntity.ok(response);
    }


}
