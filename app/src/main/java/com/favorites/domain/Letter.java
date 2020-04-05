package com.favorites.domain;

import com.favorites.domain.enums.LetterType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 私信
 *
 * @author lyoko
 */
@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Letter extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long sendUserId;
    @Column(nullable = false, length = 65535, columnDefinition = "Text")
    private String content;
    @Column(nullable = false)
    private Long receiveUserId;
    @Column(nullable = true)
    private Long pid;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private LetterType type;
    @Column(nullable = false)
    private Long createTime;
    @Transient
    private String sendType;
}