package com.base.domain.mapping;

import com.base.domain.user.User;
import com.base.domain.role.Role;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "user_role_map")
@IdClass(UserRoleId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleMap {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserRoleId implements Serializable {
    private Long user;
    private Long role;
}
