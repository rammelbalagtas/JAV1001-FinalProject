package com.rammelbalagtas.finalproject.models;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Pizza> pizzaList;
    private double subTotal = 0.0;
    private double total = 0.0;
    private double tax = 0.0;

    public Cart() {
        pizzaList = new ArrayList<>();
    }

    public void addPizza(Pizza pizza) {
        pizzaList.add(pizza);
        computeTotal();
    }

    public void removePizza(int index) {
        pizzaList.remove(index);
        computeTotal();
    }

    private void computeTotal() {

    }
}
