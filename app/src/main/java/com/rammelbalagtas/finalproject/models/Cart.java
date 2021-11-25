package com.rammelbalagtas.finalproject.models;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Pizza> pizzaList;
    private double subTotal;
    private double tax;
    private double total;
    private final double taxRate = 0.13;

    public Cart() {
        pizzaList = new ArrayList<>();
        subTotal = 0.0;
        tax = 0.0;
        total = 0.0;
    }

    public ArrayList<Pizza> getPizzaList() {
        return pizzaList;
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
        for (Pizza pizza: pizzaList) {
            subTotal += pizza.getPrice();
        }
        tax = subTotal * taxRate;
        total = subTotal + tax;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }
}
