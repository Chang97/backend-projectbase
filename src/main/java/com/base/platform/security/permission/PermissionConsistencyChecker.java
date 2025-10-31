package com.base.platform.security.permission;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.base.contexts.authr.permission.application.query.dto.PermissionQueryResult;
import com.base.contexts.authr.permission.application.query.port.in.GetPermissionsUseCase;

import lombok.RequiredArgsConstructor;

/**
 * @PreAuthorize에 선언된 권한 코드와 DB permission_code를 비교해 싱크를 검증한다.
 */
@Component
@RequiredArgsConstructor
public class PermissionConsistencyChecker implements ApplicationRunner {

    private static final Pattern HAS_AUTHORITY_PATTERN =
            Pattern.compile("hasAuthority\\(['\"]([A-Z0-9_:.-]+)['\"]\\)");

    private final ApplicationContext applicationContext;
    private final GetPermissionsUseCase getPermissionsUseCase;

    @Override
    public void run(ApplicationArguments args) {
        Set<String> dbPermissions = getPermissionsUseCase.handle(null).stream()
                .map(PermissionQueryResult::permissionCode)
                .collect(Collectors.toUnmodifiableSet());

        Set<String> referencedPermissions = resolvePermissionsFromAnnotations();
        Set<String> missing = referencedPermissions.stream()
                .filter(code -> !dbPermissions.contains(code))
                .collect(Collectors.toCollection(HashSet::new));

        if (!missing.isEmpty()) {
            throw new IllegalStateException("등록되지 않은 permission_code가 사용되었습니다: " + missing);
        }
    }

    private Set<String> resolvePermissionsFromAnnotations() {
        return Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(applicationContext::getType)
                .filter(Objects::nonNull)
                .flatMap(type -> {
                    List<String> acc = new ArrayList<>();
                    collectFromElement(type, acc);
                    ReflectionUtils.doWithMethods(type, method -> collectFromElement(method, acc));
                    return acc.stream();
                })
                .collect(Collectors.toCollection(HashSet::new));
    }

    private void collectFromElement(AnnotatedElement element, List<String> sink) {
        PreAuthorize annotation = AnnotatedElementUtils.findMergedAnnotation(element, PreAuthorize.class);
        if (annotation == null) {
            return;
        }

        Matcher matcher = HAS_AUTHORITY_PATTERN.matcher(annotation.value());
        while (matcher.find()) {
            sink.add(matcher.group(1));
        }
    }
}
