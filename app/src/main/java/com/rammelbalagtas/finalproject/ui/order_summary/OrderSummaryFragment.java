package com.rammelbalagtas.finalproject.ui.order_summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.adapter.OrderSummaryAdapter;
import com.rammelbalagtas.finalproject.adapter.PizzaListAdapter;
import com.rammelbalagtas.finalproject.databinding.FragmentOrderSummaryBinding;
import com.rammelbalagtas.finalproject.models.Pizza;

import java.util.ArrayList;

public class OrderSummaryFragment extends Fragment {

    private final ArrayList<Pizza> pizzaList = new ArrayList<>();
    private FragmentOrderSummaryBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInitialData();
    }

    private void setInitialData() {
        // create dummy data
        pizzaList.add(new Pizza("Name 1", "Description", 10));
        pizzaList.add(new Pizza("Name 2", "Description", 20));
        pizzaList.add(new Pizza("Name 3", "Description", 30));
        pizzaList.add(new Pizza("Name 4", "Description", 40));
        pizzaList.add(new Pizza("Name 5", "Description", 50));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the fragment
        binding = FragmentOrderSummaryBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        // Create recycler view
        RecyclerView recycler = rootView.findViewById(R.id.pizza_order_recycler_view);
        // Set the default layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(layoutManager);
        // Create list adapter
        recycler.setAdapter(new OrderSummaryAdapter(pizzaList));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
