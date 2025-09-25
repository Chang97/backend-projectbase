package com.api.user;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.user.dto.UserResponse;
import com.application.user.UserCommandService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserCommandService userCommandService; // final로 불변성 보장

    public UserController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    // 진단용
    @GetMapping("/ping")
    public Map<String,Object> ping(){ return Map.of("ok", true); }
    
    // 숫자만 매칭되게 제약
    @GetMapping("/{id:\\d+}")  
    public ResponseEntity<UserResponse> get(@PathVariable Long id){
        UserResponse userInfo = userCommandService.findById(id);
        return userInfo != null ? ResponseEntity.ok(userInfo) : ResponseEntity.notFound().build();
    }



}
