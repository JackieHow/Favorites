package com.favorites.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:
 * @Param:
 * @return:
 * @Author: lyoko
 * @Date: 2019/11/25 17:53
 */
@Entity
@Data
@Accessors(chain = true)
public class UrlLibrary extends Entitys implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "varchar(600)")
    private String url;
    @Column(nullable = true, columnDefinition = "varchar(300)")
    private String logoUrl;
    @Column(columnDefinition = "INT default 0")
    private int count;
}
