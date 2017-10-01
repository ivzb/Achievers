package com.achievers.data.entities;

import com.achievers.R;

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

    public int getDrawable() {
        switch (this.getId()) {
            case 1: return R.drawable.bronze;
            case 2: return R.drawable.silver;
            case 3: return R.drawable.gold;
            case 4: return R.drawable.platinum;
            case 5: return R.drawable.diamond;
        }

        return 0;
    }
}