package com.rammelbalagtas.finalproject.ui.specialties;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rammelbalagtas.finalproject.databinding.FragmentPizzalistRecyclerBinding;
import com.rammelbalagtas.finalproject.models.Pizza;
import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.adapter.PizzaListAdapter;

import java.util.ArrayList;

public class PizzaListFragment extends Fragment {

    private PizzaListAdapter listAdapter;
    private ArrayList<Pizza> pizzaList = new ArrayList<>();
    private RecyclerView recycler;
    private FragmentPizzalistRecyclerBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize dataset, this data would usually come from a local content provider or
        // remote server
        pizzaList.add(new Pizza("Pizza 1", "Description 1"));
        pizzaList.add(new Pizza("Pizza 2", "Description 2"));
        pizzaList.add(new Pizza("Pizza 3", "Description 3"));
        pizzaList.add(new Pizza("Pizza 4", "Description 4"));
        pizzaList.add(new Pizza("Pizza 5", "Description 5"));
        pizzaList.add(new Pizza("Pizza 6", "Description 6"));
        pizzaList.add(new Pizza("Pizza 7", "Description 7"));
        pizzaList.add(new Pizza("Pizza 8", "Description 8"));
        pizzaList.add(new Pizza("Pizza 9", "Description 9"));
        pizzaList.add(new Pizza("Pizza 10", "Description 10"));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the fragment
        binding = FragmentPizzalistRecyclerBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        // Create recycler view
        recycler = rootView.findViewById(R.id.pizza_recycler_view);
        // Set the default layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(layoutManager);
        // Create list adapter
        listAdapter = new PizzaListAdapter(pizzaList);
        recycler.setAdapter(listAdapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}