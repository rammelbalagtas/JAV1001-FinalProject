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
import com.rammelbalagtas.finalproject.helper.PizzaDataConfiguration;

import java.util.ArrayList;
import java.util.Collections;

public class PizzaListFragment extends Fragment {

    private final ArrayList<Pizza> pizzaList = new ArrayList<>();
    private FragmentSpecialPizzaBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInitialData();
    }

    private void setInitialData() {
        Collections.addAll(pizzaList, PizzaDataConfiguration.pizzaSpecials);
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