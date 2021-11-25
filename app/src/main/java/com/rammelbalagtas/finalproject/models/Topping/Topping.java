package com.rammelbalagtas.finalproject.models.Topping;

import java.io.Serializable;

public class Topping implements Serializable {
    private String type;
    private String name;
    private String level;

    public Topping(String type, String name, String level) {
        this.type = type;
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
