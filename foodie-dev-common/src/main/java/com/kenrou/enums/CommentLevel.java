package com.kenrou.enums;

/**
 * @Desc: 商品评价等级 枚举
 */
public enum CommentLevel {
    good(1, "好评"),
    normal(2, "中评"),
    bad(3, "差评");

    public final Integer type;
    public final String value;

    CommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
