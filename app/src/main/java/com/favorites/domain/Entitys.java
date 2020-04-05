package com.favorites.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @Description: 重写to String 方法
 * @Param:
 * @return:
 * @Author: lyoko
 * @Date: 2019/11/25 17:55
 */
public class Entitys implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

