package com.base.infra.mybatis.user;

import org.apache.ibatis.annotations.Mapper;

import com.base.api.user.dto.UserResponse;

@Mapper
public interface UserMapper {

    UserResponse findById(Long id);
}
