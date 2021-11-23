package com.rammelbalagtas.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.rammelbalagtas.finalproject.models.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    public AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // Set up bottom navigation toolbar and navigation controller
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_order_history)
                .build();
        NavigationUI.setupActionBarWithNavController(this,
                navController,
                appBarConfiguration);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
//        getMenuInflater().inflate(R.menu.options_menu, menu);
//        MenuItem item = menu.findItem(R.id.view_cart);
//        MenuItemCompat.setActionView(item, R.layout.view_cart_action_layout);
//        ConstraintLayout notifCount = (ConstraintLayout) MenuItemCompat.getActionView(item);
//        TextView tv = notifCount.findViewById(R.id.cart_itemcount_text);
//        tv.setText("(12)");
//        return super.onCreateOptionsMenu(menu);
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.view_cart) {
            displayCart();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void displayCart() {
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.navigate_to_cart);
    }

    // Need to be implemented to support navigate back functionality
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static void saveSharedPreferences(Cart cart, @NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(cart);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SharedObject", json);
//        editor.commit();
        editor.apply();
    }

    public static void saveCart(Cart cart, @NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(cart);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Cart", json);
        editor.apply();
    }

    public static Cart getSharedPreferences(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("SharedObject", "null");
        return gson.fromJson(json, Cart.class);
    }
}