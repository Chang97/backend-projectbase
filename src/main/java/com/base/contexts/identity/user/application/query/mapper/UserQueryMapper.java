package com.base.contexts.identity.user.application.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.contexts.identity.user.application.query.dto.LoginIdAvailabilityResult;
import com.base.contexts.identity.user.application.query.dto.UserQuery;
import com.base.contexts.identity.user.application.query.dto.UserQueryResult;
import com.base.contexts.identity.user.domain.model.User;
import com.base.contexts.identity.user.domain.model.UserFilter;
import com.base.shared.core.util.StringNormalizer;

@Component
public class UserQueryMapper {

    public UserFilter toFilter(UserQuery query) {
        if (query == null) {
            return UserFilter.empty();
        }
        return new UserFilter(
                query.userId(),
                StringNormalizer.trimToNull(query.email()),
                StringNormalizer.trimToNull(query.loginId()),
                StringNormalizer.trimToNull(query.userName()),
                query.orgId(),
                query.statusCodeId(),
                query.useYn(),
                null
        );
    }

    public UserQueryResult toResult(User user, List<Long> roleIds) {
        return new UserQueryResult(
                user.getUserId() != null ? user.getUserId().value() : null,
                user.getEmail(),
                user.getLoginId(),
                user.getUserName(),
                user.getOrgId(),
                user.getEmpNo(),
                user.getPositionName(),
                user.getTel(),
                user.getStatusCodeId(),
                user.getUseYn(),
                user.getPasswordUpdatedAt(),
                user.getPasswordFailCount(),
                roleIds
        );
    }

    public LoginIdAvailabilityResult toLoginIdAvailability(String loginId, boolean available) {
        return new LoginIdAvailabilityResult(loginId, available);
    }
}
