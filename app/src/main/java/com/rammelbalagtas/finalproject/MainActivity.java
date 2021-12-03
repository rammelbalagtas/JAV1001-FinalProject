package com.rammelbalagtas.finalproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rammelbalagtas.finalproject.helper.DataPersistence;
import com.rammelbalagtas.finalproject.models.Cart;
import com.rammelbalagtas.finalproject.ui.home.HomeFragmentDirections;

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
    }

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
        Cart cart = DataPersistence.getCartSF(getApplicationContext());
        if (cart.getPizzaList().size() == 0) {
            Toast.makeText(getApplicationContext(), "Your cart is currently empty", Toast.LENGTH_SHORT).show();
        } else {
            Navigation.findNavController(this, R.id.nav_host_fragment).
                    navigate(HomeFragmentDirections.actionNavToOrderSummary(null));
        }
    }

    // Need to be implemented to support navigate back functionality
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}