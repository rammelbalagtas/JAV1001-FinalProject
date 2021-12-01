package com.rammelbalagtas.finalproject.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
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
        updateTotal(pizza, "Add");
    }

    public void removePizza(int index) {
        updateTotal(pizzaList.get(index), "Delete");
        pizzaList.remove(index);
    }

    private void computeTotal() {
        for (Pizza pizza: pizzaList) {
            subTotal += (pizza.getPrice() * pizza.getQuantity());
        }
        tax = subTotal * taxRate;
        total = subTotal + tax;
    }

    private void updateTotal(Pizza pizza, String action){
        double tempSubTotal = (pizza.getPrice() * pizza.getQuantity());
        double tempTax = tempSubTotal * taxRate;
        if (action.equals("Add")) {
            subTotal += tempSubTotal;
            tax += tempTax;
            total = total + tempSubTotal + tempTax;
        } else {
            subTotal -= tempSubTotal;
            tax -= tempTax;
            total = total - tempSubTotal - tempTax;
        }
    }
}
