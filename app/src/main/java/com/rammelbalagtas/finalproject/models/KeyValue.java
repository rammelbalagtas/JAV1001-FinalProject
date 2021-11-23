package com.rammelbalagtas.finalproject.models;

import java.util.HashMap;

public class KeyValue {
    public HashMap<String, String> pizzaCrust;
    public HashMap<String, String> pizzaSize;

    public KeyValue(HashMap<String, String> pizzaCrust, HashMap<String, String> pizzaSize) {
        this.pizzaCrust = pizzaCrust;
        this.pizzaSize = pizzaSize;
    }
}
