package com.base.contexts.identity.user.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.base.contexts.identity.user.adapter.out.persistence.entity.UserEntity;
import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.model.UserId;

@Component
public class UserEntityMapper {

    public UserEntity toNewEntity(User user) {
        UserEntity entity = new UserEntity();
        apply(user, entity);
        return entity;
    }

    public void merge(User user, UserEntity target) {
        apply(user, target);
    }

    private void apply(User user, UserEntity target) {
        target.setEmail(user.getEmail());
        target.setLoginId(user.getLoginId());
        target.setUserPassword(user.getPassword());
        target.setUserName(user.getUserName());
        target.setOrgId(user.getOrgId());
        target.setEmpNo(user.getEmpNo());
        target.setPstnName(user.getPositionName());
        target.setTel(user.getTel());
        target.setUserStatusCodeId(user.getStatusCodeId());
        target.setUserPasswordUpdateDt(user.getPasswordUpdatedAt());
        target.setUserPasswordFailCnt(user.getPasswordFailCount());
        target.setOld1UserPassword(user.getPreviousPassword());
        target.setUseYn(Boolean.TRUE.equals(user.getUseYn()));
    }

    public User toDomain(UserEntity entity) {
        return User.restore(
                UserId.of(entity.getUserId()),
                entity.getEmail(),
                entity.getLoginId(),
                entity.getUserPassword(),
                entity.getUserName(),
                entity.getOrgId(),
                entity.getEmpNo(),
                entity.getPstnName(),
                entity.getTel(),
                entity.getUserStatusCodeId(),
                entity.getUserPasswordUpdateDt(),
                entity.getUserPasswordFailCnt(),
                entity.getOld1UserPassword(),
                entity.getUseYn()
        );
    }
}
