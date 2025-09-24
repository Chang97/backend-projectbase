package com.infra.mybatis.user;

import org.apache.ibatis.annotations.Mapper;

import com.api.user.dto.UserResponse;

@Mapper
public interface UserMapper {

    UserResponse findById(Long id);
}
