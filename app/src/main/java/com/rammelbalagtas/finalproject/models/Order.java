package com.rammelbalagtas.finalproject.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private int orderId;
    private String orderStatus;
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

    public ArrayList<Pizza> getPizzaList() {
        return pizzaList;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    private void computeTotal() {

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

    public int getOrderId() {
        return orderId;
    }
}
