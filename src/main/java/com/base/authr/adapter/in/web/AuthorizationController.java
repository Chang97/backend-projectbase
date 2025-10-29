package com.base.authr.adapter.in.web;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.authr.adapter.in.web.dto.MenuTreeResponse;
import com.base.authr.adapter.in.web.mapper.AuthorizationResponseMapper;
import com.base.authr.application.dto.MenuTreeResult;
import com.base.authr.application.port.in.GetResolveMenusUseCase;

@RestController
@RequestMapping("/api/authr")
@Validated
@RequiredArgsConstructor
public class AuthorizationController {

    private final GetResolveMenusUseCase getResolveMenusUseCase;
    private final AuthorizationResponseMapper authorizationResponseMapper;

    @GetMapping("/resolveMenus/{userId}")
    public ResponseEntity<List<MenuTreeResponse>> getResolveMenus(@PathVariable Long userId) {
        List<MenuTreeResult> result = getResolveMenusUseCase.handle(userId);                // List<MenuTreeResult>
        List<MenuTreeResponse> response = authorizationResponseMapper.toMenuTreeResponses(result); // ✔ 리스트 변환 호출
        return ResponseEntity.ok(response);                                // 중복 return 제거
    }


}
