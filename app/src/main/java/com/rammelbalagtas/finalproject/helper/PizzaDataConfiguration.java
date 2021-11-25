package com.rammelbalagtas.finalproject.helper;

import com.rammelbalagtas.finalproject.models.Pizza;

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
            {"Pepperoni", "Sauce", "Beef", "Ham", "Bacon", "Chicken"};
    public static final String[] vegetableTopping =
            {"Black Olives", "Green Olives", "Green Pepper", "Mushroom", "Pineapple", "Onion", "Tomatoes",
                    "Jalapeno Peppers", "Baby Spinach"};
    public static final Pizza[] pizzaSpecials =
            {
                    new Pizza("Tuscan Pesto", "Pesto Sauce, Grilled Chicken, Roasted Red Peppers", 10.0),
                    new Pizza("Veggie", "Fresh Mushroom, Green Peppers, Spanish Onions", 12.0),
                    new Pizza("Super Hawaiian", "Smoked Ham, Pineapple, Bacon", 14.0),
                    new Pizza("Meat Supreme", "Pepperoni, Bacon, Ground Beef, Spicy Sauce", 16.0),
                    new Pizza("Deluxe", "Pepperoni, Fresh Mushrooms, Green Peppers, Bacon, Sliced Tomatoes", 18.0)
            };

    static {

    }

}