package com.rammelbalagtas.finalproject.models;

import com.rammelbalagtas.finalproject.models.Topping.Meat;
import com.rammelbalagtas.finalproject.models.Topping.Sauce;
import com.rammelbalagtas.finalproject.models.Topping.Vegetable;

import java.io.Serializable;
import java.util.ArrayList;

public class Pizza implements Serializable {

    private String name;
    private String description;
    private String size;
    private String crust;
    private int quantity;
    private double price;

    private ArrayList<Sauce> sauceList = new ArrayList<>();
    private ArrayList<Meat> meatList = new ArrayList<>();
    private ArrayList<Vegetable> vegetableList = new ArrayList<>();

    public Pizza() {
        // empty constructor
    }

    public Pizza(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Pizza(String name, String description, int quantity) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public String getCrust() {
        return crust;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<Sauce> getSauceList() {
        return sauceList;
    }

    public ArrayList<Meat> getMeatList() {
        return meatList;
    }

    public ArrayList<Vegetable> getVegetableList() {
        return vegetableList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSauceList(ArrayList<Sauce> sauceList) {
        this.sauceList = sauceList;
    }

    public void setMeatList(ArrayList<Meat> meatList) {
        this.meatList = meatList;
    }

    public void setVegetableList(ArrayList<Vegetable> vegetableList) {
        this.vegetableList = vegetableList;
    }

    public String buildToppingText() {
        String text = "";
        for (Sauce sauce : sauceList) {
            if (!sauce.getLevel().equals("None")) {
                text = text + " " + sauce.getName();
            }
        }

        for (Meat meat : meatList) {
            if (!meat.getLevel().equals("None")) {
                text = text + " " + meat.getName();
            }
        }

        for (Vegetable vegetable : vegetableList) {
            if (!vegetable.getLevel().equals("None")) {
                text = text + " " + vegetable.getName();
            }
        }
        return text;
    }
}
