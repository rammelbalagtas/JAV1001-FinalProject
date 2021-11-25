package com.rammelbalagtas.finalproject.models.Topping;

import java.io.Serializable;

public class Vegetable extends Topping implements Serializable {
    public Vegetable(String name, String level) {
        super("Vegetable", name, level);
    }
}
