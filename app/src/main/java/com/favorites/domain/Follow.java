package com.favorites.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.favorites.domain.enums.FollowStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 关注
 *
 * @author lyoko
 */
@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Follow extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long followId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FollowStatus status;
    @Column(nullable = false)
    private Long createTime;
    @Column(nullable = false)
    private Long lastModifyTime;
    @Transient
    private String name;
}