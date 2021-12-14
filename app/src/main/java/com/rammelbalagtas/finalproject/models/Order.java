package com.rammelbalagtas.finalproject.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private int orderId;
    private String status;
    private ArrayList<Pizza> pizzaList;
    private double subTotal;
    private double tax;
    private double total;
    private final double taxRate = 0.13;

    public Order(int orderId) {
        this.orderId = orderId;
    }

    public ArrayList<Pizza> getPizzaList() {
        return pizzaList;
    }
    public String getStatus() { return status; }
    public double getSubTotal() {
        return subTotal;
    }
    public double getTax() {
        return tax;
    }
    public double getTotal() {
        return total;
    }
    public int getOrderId() {
        return orderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setPizzaList(ArrayList<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
    }
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
    public void setTax(double tax) {
        this.tax = tax;
    }
    public void setTotal(double total) {
        this.total = total;
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
