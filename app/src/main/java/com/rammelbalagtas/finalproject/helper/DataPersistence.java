package com.rammelbalagtas.finalproject.helper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.rammelbalagtas.finalproject.models.Cart;
import com.rammelbalagtas.finalproject.models.OrderList;
import com.rammelbalagtas.finalproject.models.Pizza;

public class DataPersistence {

    private static final String PREFERENCE_NAME = "SharedPreferences2";

    public static void saveCartSF(Cart cart, @NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(cart);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Cart", json);
        editor.apply();
    }

    public static Cart getCartSF(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Cart", null);
        if (json != null) {
            return gson.fromJson(json, Cart.class);
        } else {
            return new Cart();
        }
    }

    public static void deleteCartSF(@NonNull Context context) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove("Cart")
                .apply();
    }

    public static void resetCartSF(Cart cart, @NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Cart", null);
        editor.apply();
    }

    public static void saveOrderListSF(OrderList orderList, @NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(orderList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("OrderList", json);
        editor.apply();
    }

    public static OrderList getOrderListSF(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("OrderList", null);
        if (json != null) {
            return gson.fromJson(json, OrderList.class);
        } else {
            return new OrderList();
        }
    }

    public static void resetOrderListSF(OrderList orderList, @NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("OrderList", null);
        editor.apply();
    }

    public static void savePizzaSF(Pizza pizza, @NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(pizza);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Pizza", json);
        editor.apply();
    }

    public static Pizza getPizzaSF(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Pizza", null);
        if (json != null) {
            return gson.fromJson(json, Pizza.class);
        } else {
            return new Pizza();
        }
    }

    public static void deletePizzaSF(Pizza pizza, @NonNull Context context) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove("Pizza")
                .apply();
    }

}
