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

//    private void updateTotal(Pizza pizza, String action){
//        double tempSubTotal = (pizza.getPrice() * pizza.getQuantity());
//        double tempTax = tempSubTotal * taxRate;
//        if (action.equals("Add")) {
//            subTotal += tempSubTotal;
//            tax += tempTax;
//            total = total + tempSubTotal + tempTax;
//        } else {
//            subTotal -= tempSubTotal;
//            tax -= tempTax;
//            total = total - tempSubTotal - tempTax;
//        }
//    }

}
