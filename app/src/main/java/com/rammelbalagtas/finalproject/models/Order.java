package com.rammelbalagtas.finalproject.models;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private int orderStatus;
    private ArrayList<Pizza> pizzaList;
    private double subTotal;
    private double tax;
    private double total;

    public Order(int orderId) {
        this.orderId = orderId;
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

    public int getOrderId() {
        return orderId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }
}
