package com.rammelbalagtas.finalproject.ui.special_pizza;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rammelbalagtas.finalproject.databinding.FragmentSpecialPizzaBinding;
import com.rammelbalagtas.finalproject.models.Pizza;
import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.adapter.PizzaListAdapter;

import java.util.ArrayList;

public class PizzaListFragment extends Fragment {

    private final ArrayList<Pizza> pizzaList = new ArrayList<>();
    private FragmentSpecialPizzaBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInitialData();
    }

    private void setInitialData() {
        // create dummy data
        pizzaList.add(new Pizza("Tuscan Pesto", "Pesto Sauce, Grilled Chicken, Roasted Red Peppers", 10.0));
        pizzaList.add(new Pizza("Veggie", "Fresh Mushroom, Green Peppers, Spanish Onions", 12.0));
        pizzaList.add(new Pizza("Super Hawaiian", "Smoked Ham, Pineapple, Bacon", 14.0));
        pizzaList.add(new Pizza("Meat Supreme", "Pepperoni, Bacon, Ground Beef, Spicy Sauce", 16.0));
        pizzaList.add(new Pizza("Deluxe", "Pepperoni, Fresh Mushrooms, Green Peppers, Bacon, Sliced Tomatoes", 18.0));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the fragment
        binding = FragmentSpecialPizzaBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        // Create recycler view
        RecyclerView recycler = rootView.findViewById(R.id.pizza_recycler_view);
        // Set the default layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(layoutManager);
        // Create list adapter
        recycler.setAdapter(new PizzaListAdapter(pizzaList));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}