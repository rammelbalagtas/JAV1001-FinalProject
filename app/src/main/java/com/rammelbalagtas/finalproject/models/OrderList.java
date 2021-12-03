package com.rammelbalagtas.finalproject.models;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderList implements Serializable {

    private final ArrayList<Order> orders;

    public OrderList() {
        this.orders = new ArrayList<>();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrder(int index) {
        orders.remove(index);
    }

    public void updateOrder(Order order, int index) {
        orders.set(index, order);
    }
}
