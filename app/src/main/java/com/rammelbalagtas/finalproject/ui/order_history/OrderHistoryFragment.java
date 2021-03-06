package com.rammelbalagtas.finalproject.ui.order_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.adapter.OrderListAdapter;
import com.rammelbalagtas.finalproject.databinding.FragmentOrderHistoryBinding;
import com.rammelbalagtas.finalproject.helper.DataPersistence;
import com.rammelbalagtas.finalproject.models.Cart;
import com.rammelbalagtas.finalproject.models.OrderList;
import com.rammelbalagtas.finalproject.ui.home.HomeFragmentDirections;

public class OrderHistoryFragment extends Fragment implements IOrderList {

    private OrderList orderList;
    private FragmentOrderHistoryBinding binding;
    private OrderListAdapter adapter;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Enabled options menu
        setHasOptionsMenu(true);

        setInitialData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false);
        rootView = binding.getRoot();
        // Create recycler view
        RecyclerView recycler = rootView.findViewById(R.id.order_list_recycler_view);
        // Set the default layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(layoutManager);
        // Create list adapter
        adapter = new OrderListAdapter(orderList.getOrders(), this);
        recycler.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
        Cart cart = DataPersistence.getCartSF(getContext());
        if (cart.getPizzaList().size() == 0) {
            Toast.makeText(getContext(), "Your cart is currently empty", Toast.LENGTH_SHORT).show();
        } else {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).
                    navigate(HomeFragmentDirections.actionNavToOrderSummary(null, 0));
        }
    }

    private void setInitialData() {
        // Extract data from shared preferences
        orderList = DataPersistence.getOrderListSF(requireContext());
    }

    @Override
    public void edit(int position) {
        Navigation.findNavController(rootView).
                navigate(OrderHistoryFragmentDirections.actionNavOrderHistoryToOrderSummary(orderList.getOrders().get(position), position));
    }

    @Override
    public void remove(int position) {
          orderList.removeOrder(position);
          DataPersistence.saveOrderListSF(orderList, requireContext());
          adapter.notifyItemRemoved(position);
    }
}
