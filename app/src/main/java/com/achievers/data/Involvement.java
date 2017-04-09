package com.achievers.data;

public enum Involvement {
    Bronze(1),
    Silver(2),
    Gold(3),
    Platinum(4),
    Diamond(5);

    private int id;

    Involvement(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}