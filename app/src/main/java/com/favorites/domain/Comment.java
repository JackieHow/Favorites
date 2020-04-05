package com.favorites.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 评论
 *
 * @author lyoko
 */
@Entity
@Data
@Accessors(chain = true)
public class Comment extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long collectId;
    @Column(nullable = false, length = 65535, columnDefinition = "Text")
    private String content;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = true)
    private Long replyUserId;
    @Column(nullable = false)
    private Long createTime;
    @Transient
    private String commentTime;
    @Transient
    private String userName;
    @Transient
    private String replyUserName;
    @Transient
    private String profilePicture;
}