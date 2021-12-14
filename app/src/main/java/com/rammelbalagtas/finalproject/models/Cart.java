package com.rammelbalagtas.finalproject.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
    private final ArrayList<Pizza> pizzaList;
    private double subTotal;
    private double tax;
    private double total;
    private final double taxRate = 0.13;

    public Cart() {
        pizzaList = new ArrayList<>();
        initTotal();
    }

    public ArrayList<Pizza> getPizzaList() {
        return pizzaList;
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

    public void addPizza(Pizza pizza) {
        pizzaList.add(pizza);
        computeTotal();
    }

    public void removePizza(int index) {
        pizzaList.remove(index);
        if (!pizzaList.isEmpty()) {
            computeTotal();
        } else {
            initTotal();
        }
    }

    public void computeTotal() {
        initTotal();
        for (Pizza pizza: pizzaList) {
            subTotal = subTotal + (pizza.getPrice() * pizza.getQuantity());
        }
        tax = subTotal * taxRate;
        total = subTotal + tax;
    }

    public void initTotal() {
        subTotal = 0.0;
        tax = 0.0;
        total = 0.0;
    }

}
