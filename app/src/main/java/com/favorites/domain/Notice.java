package com.favorites.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 消息
 *
 * @author lyoko
 */
@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Notice extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = true)
    private String collectId;
    @Column(nullable = false)
    private String type;
    @Column(nullable = true)
    private String operId;
    @Column(nullable = false)
    private String readed;
    @Column(nullable = false)
    private Long createTime;
}