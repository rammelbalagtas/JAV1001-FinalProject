package com.rammelbalagtas.finalproject.models.Topping;

import java.io.Serializable;

public class Meat extends Topping implements Serializable {
    public Meat(String name, String level) {
        super("Meat", name, level);
    }
}
