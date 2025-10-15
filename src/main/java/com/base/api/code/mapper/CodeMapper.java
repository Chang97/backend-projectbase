package com.base.api.code.mapper;

import com.base.api.code.dto.CodeRequest;
import com.base.api.code.dto.CodeResponse;
import com.base.domain.code.Code;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CodeMapper {

    @Mapping(target = "upperCode", ignore = true)
    @Mapping(target = "orderPath", ignore = true)
    Code toEntity(CodeRequest request);

    @Mapping(source = "upperCode.codeId", target = "upperCodeId")
    @Mapping(target = "depth", expression = "java(calculateDepth(code))")
    @Mapping(target = "path", expression = "java(buildPath(code))")
    CodeResponse toResponse(Code code);

    @Mapping(target = "upperCode", ignore = true)
    @Mapping(target = "orderPath", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void updateFromRequest(CodeRequest request, @MappingTarget Code entity);

    /**
     * 상위 코드 체인을 타고 내려가면서 노드의 깊이를 계산한다.
     */
    default Integer calculateDepth(Code code) {
        int depth = 0;
        Code current = code != null ? code.getUpperCode() : null;
        while (current != null) {
            depth++;
            current = current.getUpperCode();
        }
        return depth;
    }

    /**
     * 코드명(없으면 코드 값)을 상위 코드부터 순서대로 나열해 사용자에게 보여줄 경로 문자열을 생성한다.
     * 예시: [2] 그룹 > 하위 > 코드
     */
    default String buildPath(Code code) {
        if (code == null) {
            return "";
        }
        List<String> segments = new ArrayList<>();
        Code current = code;
        while (current != null) {
            String name = current.getCodeName();
            if (name == null || name.isBlank()) {
                name = current.getCode();
            }
            segments.add(name);
            current = current.getUpperCode();
        }
        Collections.reverse(segments);
        int resolvedDepth = Math.max(segments.size() - 1, 0);
        return "[" + resolvedDepth + "] " + String.join(" > ", segments);
    }
}
