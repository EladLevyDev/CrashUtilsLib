package com.crashreportutils.android;

/**
 * Created by sacredoon on 8/20/15.
 */

public class User {
    private String name;
    private int order;

    public User(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
