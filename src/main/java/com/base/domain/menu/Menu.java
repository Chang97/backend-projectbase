package com.base.domain.menu;

import jakarta.persistence.*;
import lombok.*;

import com.base.domain.common.BaseEntity;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_code", length = 50, nullable = false, unique = true)
    private String menuCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upper_menu_id")
    private Menu upperMenu;

    @Column(name = "menu_name", length = 200, nullable = false)
    private String menuName;

    @Column(name = "menu_cn", length = 400)
    private String menuCn;

    @Column(length = 300)
    private String url;

    private Integer srt;
}