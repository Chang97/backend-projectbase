package com.base.authn.adapter.in.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.base.authn.adapter.in.web.dto.LoginResponse;
import com.base.authn.adapter.in.web.dto.LoginResult;
import com.base.authn.adapter.in.web.dto.MenuResponse;
import com.base.authn.adapter.in.web.dto.MenuTreeResponse;
import com.base.authn.adapter.in.web.dto.UserSummaryResponse;
import com.base.authn.application.usecase.result.AuthExecutionResult;
import com.base.authn.application.usecase.result.AuthMenuItem;
import com.base.authn.application.usecase.result.AuthMenuTreeNode;
import com.base.authn.application.usecase.result.AuthSession;
import com.base.authn.application.usecase.result.AuthUserSnapshot;

@Component
public class AuthResponseAssembler {

    public LoginResult toLoginResult(AuthExecutionResult executionResult) {
        LoginResponse response = toLoginResponse(executionResult.session());
        return new LoginResult(response, executionResult.cookies());
    }

    public LoginResponse toLoginResponse(AuthSession session) {
        return new LoginResponse(
                toUserResponse(session.user()),
                toMenuTreeResponses(session.menuTree()),
                toMenuResponses(session.accessibleMenus()),
                session.permissions()
        );
    }

    private UserSummaryResponse toUserResponse(AuthUserSnapshot snapshot) {
        if (snapshot == null) {
            return null;
        }
        return new UserSummaryResponse(
                snapshot.userId(),
                snapshot.email(),
                snapshot.loginId(),
                snapshot.userName(),
                snapshot.orgId(),
                snapshot.empNo(),
                snapshot.positionName(),
                snapshot.tel(),
                snapshot.useYn()
        );
    }

    private List<MenuTreeResponse> toMenuTreeResponses(List<AuthMenuTreeNode> nodes) {
        return nodes == null ? List.of() : nodes.stream()
                .map(this::toMenuTreeResponse)
                .toList();
    }

    private MenuTreeResponse toMenuTreeResponse(AuthMenuTreeNode node) {
        if (node == null) {
            return null;
        }
        List<MenuTreeResponse> children = toMenuTreeResponses(node.children());
        return new MenuTreeResponse(node.menuId(), node.menuCode(), node.menuName(), node.url(), children);
    }

    private List<MenuResponse> toMenuResponses(List<AuthMenuItem> items) {
        return items == null ? List.of() : items.stream()
                .map(item -> new MenuResponse(item.menuId(), item.menuCode(), item.menuName(), item.url()))
                .toList();
    }
}
