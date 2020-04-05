package com.favorites.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户反馈javaBean类
 * Created by chenzhimin on 2017/2/23.
 */
@Entity
@Data
@Accessors(chain = true)
public class Feedback extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private Long userId;
    @Column(nullable = false)
    private String feedbackAdvice;
    @Column(nullable = true)
    private String feedbackName;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private Long createTime;
    @Column(nullable = false)
    private Long lastModifyTime;
}
