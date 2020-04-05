package com.favorites.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description: 随便看看右侧关注使用
 * @Param:
 * @return:
 * @Author: lyoko
 * @Date: 2019/11/25 17:53
 */
@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserIsFollow implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String profilePicture;
    private String isFollow;
}
