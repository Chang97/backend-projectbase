package com.base.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
  private Long id;
  @JsonProperty("user_id") private String userId; // 내부는 카멜, 외부는 스네이크
  private String email;
  @JsonProperty("user_nm") private String userNm;
}