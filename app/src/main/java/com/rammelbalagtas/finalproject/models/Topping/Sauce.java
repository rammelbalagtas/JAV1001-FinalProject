package com.rammelbalagtas.finalproject.models.Topping;

import java.io.Serializable;

public class Sauce extends Topping implements Serializable {
    public Sauce(String name, String level) {
        super("Sauce", name, level);
    }
}
