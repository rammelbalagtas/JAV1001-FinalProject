package com.rammelbalagtas.finalproject.models;

import com.rammelbalagtas.finalproject.models.Topping.Meat;
import com.rammelbalagtas.finalproject.models.Topping.Sauce;
import com.rammelbalagtas.finalproject.models.Topping.Vegetable;

import java.util.ArrayList;

public class Pizza {

    private String name;
    private String description;
    private String dough;
    private String size;

    private ArrayList<Sauce> sauceList = new ArrayList<Sauce>();
    private ArrayList<Meat> meatTopping = new ArrayList<Meat>();
    private ArrayList<Vegetable> vegetableTopping = new ArrayList<Vegetable>();

    public Pizza(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDough() {
        return dough;
    }

    public void setDough(String dough) {
        this.dough = dough;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Sauce> getSauceList() {
        return sauceList;
    }

    public ArrayList<Meat> getMeatTopping() {
        return meatTopping;
    }

    public ArrayList<Vegetable> getVegetableTopping() {
        return vegetableTopping;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
