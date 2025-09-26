package com.base.api.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.user.dto.UserResponse;
import com.base.application.user.command.UserCommandService;
import com.base.application.user.query.UserQueryService;
import com.base.domain.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    // 전체 조회
    @GetMapping
    public List<User> findAll() {
        return userQueryService.getUsers();
    }

    // 단건 조회 (ID 기준)
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userQueryService.getUser(id);
    }

    // 이메일로 조회
    @GetMapping("/email/{email}")
    public Optional<User> findByEmail(@PathVariable String email) {
        return userQueryService.findByEmail(email);
    }

    // 로그인ID로 조회
    @GetMapping("/login/{loginId}")
    public Optional<User> findByLoginId(@PathVariable String loginId) {
        return userQueryService.findByLoginId(loginId);
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return userCommandService.saveUser(user);
    }



}
