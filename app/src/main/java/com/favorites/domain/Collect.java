package com.favorites.domain;

import com.favorites.domain.enums.CollectType;
import com.favorites.domain.enums.IsDelete;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * 未知字段 全为1
     */
    @Column(nullable = false)
    private Long favoritesId;

    /**
     * 网址
     */
    @Column(nullable = false, columnDefinition = "varchar(600)")
    private String url;

    /**
     * 标题
     */
    @Column(nullable = false)
    private String title;

    /**
     * 描述
     */
    @Column(nullable = true, length = 65535, columnDefinition = "Text")
    private String description;

    /**
     * 图标url
     */
    @Column(nullable = true, columnDefinition = "varchar(900)")
    private String logoUrl;

    /**
     * 编码格式
     */
    @Column(nullable = true)
    private String charset;

    /**
     * 是否公开 （PUBLIC, PRIVATE）
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private CollectType type;

    /**
     * 收藏备注
     */
    @Column(nullable = true)
    private String remark;

    /**
     * 删除标志 （YES, NO）
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IsDelete isDelete;

    /**
     * 创建时间
     */
    @Column(nullable = false)
    private Long createTime;

    /**
     * 正文内容
     */
    @Column(nullable = false)
    private String contentText;


    /**
     * 最后修改时间
     */
    private Long lastModifyTime;
    @Column(nullable = true)

    /**
     * 种类 （string）
     */
    private String category;

    @Transient
    private String collectTime;

    /**
     * 新建文件夹
     */
    @Transient
    private String newFavorites;
}