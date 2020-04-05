package com.favorites.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 收藏夹
 *
 * @author Lyoko
 */
@Entity
@Data
@Accessors(chain = true)
public class Favorites extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long count;
    @Column(nullable = false)
    private Long createTime;
    @Column(nullable = false)
    private Long lastModifyTime;
    @Column(nullable = false)
    private Long publicCount;
}