package com.tiansen.ordermanager.mybatis.entity.emun;

public enum ProductDefTypeEmun {
    // 产品类型 0：盈利产品 1：消耗品 2：赠品 3：非卖品 4：其他
    FOR_SALE("卖品", 0),
    CONSUMABLE("消耗品", 1),
    FOR_PRESENT("赠品", 2),
    NOT_FOR_SALE("非卖品", 3),
    OTHER("其他", 4),
    ;

    // 成员变量
    private String name;
    private int index;

    private ProductDefTypeEmun(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (ProductDefTypeEmun c : ProductDefTypeEmun.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    public static ProductDefTypeEmun getByName(String name) {
        for (ProductDefTypeEmun c : ProductDefTypeEmun.values()) {
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
