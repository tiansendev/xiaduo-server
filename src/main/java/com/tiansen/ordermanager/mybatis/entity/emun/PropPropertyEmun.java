package com.tiansen.ordermanager.mybatis.entity.emun;

public enum PropPropertyEmun {

    PROPERTY_PERSON("个人", 1),
    PROPERTY_PLATFORM("平台", 2);

    // 成员变量
    private String name;
    private int index;

    private PropPropertyEmun(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (PropPropertyEmun c : PropPropertyEmun.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    public static PropPropertyEmun getByName(String name) {
        for (PropPropertyEmun c : PropPropertyEmun.values()) {
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
