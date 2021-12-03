package com.rammelbalagtas.finalproject.helper;

import com.rammelbalagtas.finalproject.models.Pizza;
import com.rammelbalagtas.finalproject.models.Topping.Meat;
import com.rammelbalagtas.finalproject.models.Topping.Sauce;
import com.rammelbalagtas.finalproject.models.Topping.Vegetable;

import java.util.Random;

public class PizzaDataConfiguration {

    public static final String[] pizzaSize =
            {"Small (10)", "Medium (12)", "Extra (14)", "X-Large (16)"};
    public static final String[] pizzaCrust =
            {"Original Hand Tossed", "Hand Tossed Thin", "Crunchy Thin Crust", "Gluten Free Crust"};
    public static final String[] toppingLevel =
            {"None", "Less", "Normal", "Extra", "Double Extra"};
    public static final String[] sauceTopping =
            {"BBQ Sauce", "Alfredo Sauce", "Hearty Marinara Sauce", "Ranch Dressing", "Garlic Parmesan"};
    public static final String[] meatTopping =
            {"Pepperoni", "Prawn", "Beef", "Ham", "Bacon", "Chicken"};
    public static final String[] vegetableTopping =
            {"Black Olives", "Green Olives", "Green Pepper", "Mushroom", "Pineapple", "Onion", "Tomatoes",
                    "Jalapeno Peppers", "Baby Spinach"};
    public static final Pizza[] pizzaSpecials =
            {
                    new Pizza("Tuscan Pesto", "Pesto Sauce, Grilled Chicken, Roasted Red Peppers", 12.0),
                    new Pizza("Veggie", "Fresh Mushroom, Green Peppers, Spanish Onions", 14.0),
                    new Pizza("Super Hawaiian", "Smoked Ham, Pineapple, Bacon", 16.0),
                    new Pizza("Meat Supreme", "Pepperoni, Bacon, Ground Beef, Spicy Sauce", 18.0),
                    new Pizza("Deluxe", "Pepperoni, Fresh Mushrooms, Green Peppers, Bacon, Sliced Tomatoes", 20.0)
            };

    public static final int maxPizzaQuantity = 10;
    public static final int minPizzaQuantity = 1;
    public static final double pizzaBasePrice = 10.0;

    public static int generateOrderId() {
        Random rand = new Random(); //instance of random class
        return rand.nextInt(99999);
    }

    public static String buildToppingDescription(Pizza pizza) {
        String description = "Sauce: ";
        for (Sauce sauce : pizza.getSauceList()) {
            if (!sauce.getLevel().equals("None")) {
                description = description + " " + sauce.getName();
            }
        }

        String meatText = "";
        for (Meat meat : pizza.getMeatList()) {
            if (!meat.getLevel().equals("None")) {
                meatText = meatText + " " + meat.getName();
            }
        }
        if (!meatText.equals("")) {
            description = description + "\nMeat: " + meatText;
        }

        String vegetableText = "";
        for (Vegetable vegetable : pizza.getVegetableList()) {
            if (!vegetable.getLevel().equals("None")) {
                vegetableText = vegetableText + " " + vegetable.getName();
            }
        }
        if (!vegetableText.equals("")) {
            description = description + "\nVegetable: " + vegetableText;
        }
        return description;
    }
}