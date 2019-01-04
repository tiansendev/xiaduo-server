package com.tiansen.ordermanager.mybatis.entity.emun;

public enum ProductStatusEmun {
    // 0:在库 1: 正在发货途中 2: 已收货  9：退回
    IN_STORE("在库", 0),
    EXPRESS_ONGOING("正在发货途中", 1),
    RECEIVED("已收货", 2),
    RETURN_BACK("退回", 9);

    // 成员变量
    private String name;
    private int index;

    private ProductStatusEmun(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (ProductStatusEmun c : ProductStatusEmun.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    public static ProductStatusEmun getByName(String name) {
        for (ProductStatusEmun c : ProductStatusEmun.values()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
