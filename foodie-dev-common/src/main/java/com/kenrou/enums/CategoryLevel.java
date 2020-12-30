package com.kenrou.enums;

/**
 * @Desc: 性别 枚举
 */
public enum CategoryLevel {
    levelOne(1, "一级"),
    levelTwo(2, "二级"),
    levelThree(3, "三级");

    public final Integer type;
    public final String value;

    CategoryLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
