package com.rammelbalagtas.finalproject.ui.order_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.adapter.OrderListAdapter;
import com.rammelbalagtas.finalproject.adapter.OrderSummaryAdapter;
import com.rammelbalagtas.finalproject.databinding.FragmentOrderHistoryBinding;
import com.rammelbalagtas.finalproject.models.Order;

import java.util.ArrayList;

public class OrderHistoryFragment extends Fragment {

    private final ArrayList<Order> orderList = new ArrayList<Order>();
    private FragmentOrderHistoryBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInitialData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the fragment
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        // Create recycler view
        RecyclerView recycler = rootView.findViewById(R.id.order_list_recycler_view);
        // Set the default layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(layoutManager);
        // Create list adapter
        recycler.setAdapter(new OrderListAdapter(orderList));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setInitialData() {
        // create dummy data
        orderList.add(new Order(1234));
        orderList.add(new Order(1234));
        orderList.add(new Order(1234));
        orderList.add(new Order(1234));
        orderList.add(new Order(1234));
    }
}