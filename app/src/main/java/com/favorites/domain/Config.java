package com.favorites.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 属性设置
 *
 * @author lyoko
 */
@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Config extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String defaultFavorties;
    @Column(nullable = false)
    private String defaultCollectType;
    @Column(nullable = false)
    private String defaultModel;
    @Column(nullable = false)
    private Long createTime;
    @Column(nullable = false)
    private Long lastModifyTime;
    @Transient
    private String collectTypeName;
    @Transient
    private String modelName;
}