package com.base.shared.menu.adapter.out.persistence.entity;

import com.base.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_code", length = 50, nullable = false, unique = true)
    private String menuCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_menu_id")
    private MenuEntity upperMenu;

    @Column(name = "menu_name", length = 200, nullable = false)
    private String menuName;

    @Column(name = "menu_cn", length = 400)
    private String menuCn;

    @Column(length = 300)
    private String url;

    private Integer srt;
}
