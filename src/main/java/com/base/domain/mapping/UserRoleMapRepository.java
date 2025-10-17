package com.base.domain.mapping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.domain.user.User;

public interface UserRoleMapRepository extends JpaRepository<UserRoleMap, UserRoleMapId> {

    List<UserRoleMap> findAllByUser(User user);

    void deleteByUser(User user);
}
