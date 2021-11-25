package com.rammelbalagtas.finalproject.models;

import com.rammelbalagtas.finalproject.helper.DataPersistence;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderList implements Serializable {

    private ArrayList<Order> orders;

    public OrderList(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public OrderList() {
        this.orders = new ArrayList<>();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
