package com.favorites.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 点赞
 *
 * @author lyoko
 */
@Entity
@Data
@Accessors(chain = true)
public class Praise extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long collectId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long createTime;

    public Praise() {
        super();
    }
}