package com.rammelbalagtas.finalproject.ui.order_summary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.security.ConfirmationPrompt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.adapter.OrderSummaryAdapter;

import com.rammelbalagtas.finalproject.databinding.FragmentOrderSummaryBinding;
import com.rammelbalagtas.finalproject.helper.DataPersistence;
import com.rammelbalagtas.finalproject.helper.DisplayMode;
import com.rammelbalagtas.finalproject.helper.PizzaDataConfiguration;
import com.rammelbalagtas.finalproject.models.Cart;
import com.rammelbalagtas.finalproject.models.Order;
import com.rammelbalagtas.finalproject.models.OrderList;
import com.rammelbalagtas.finalproject.ui.home.HomeFragmentDirections;

import java.text.NumberFormat;

public class OrderSummaryFragment extends Fragment implements IOrderSummary {

    private Cart cart;
    private Order order;
    private FragmentOrderSummaryBinding binding;
    private OrderSummaryAdapter adapter;
    private View rootView;

    private final View.OnClickListener onClickCheckOutUpdate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (cart != null) {
                // Create a new order
                order = new Order(PizzaDataConfiguration.generateOrderId());
                order.setPizzaList(cart.getPizzaList());
                order.setSubTotal(cart.getSubTotal());
                order.setTax(cart.getTax());
                order.setTotal(cart.getTotal());
                order.setStatus("In Progress");
                OrderList orderList = DataPersistence.getOrderListSF(getContext());
                orderList.addOrder(order);
                DataPersistence.saveOrderListSF(orderList, getContext());
                DataPersistence.deleteCartSF(getContext());
                new AlertDialog.Builder(getContext())
                        .setMessage("Order ID " + String.valueOf(order.getOrderId()) + "is created.")
                        .setNeutralButton(android.R.string.ok,
                                (dialog, which) ->
                                        Navigation.findNavController(rootView).
                                                navigate(R.id.action_order_summary_fragment_to_navigation_home))
                        .create()
                        .show();
            } else {
                //Update existing order
                Navigation.findNavController(rootView).navigateUp();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = OrderSummaryFragmentArgs.fromBundle(getArguments()).getArgOrderObject();
        if (order == null) { //if order object is null, means the user is accessing the Cart
            getCartData();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the fragment
        binding = FragmentOrderSummaryBinding.inflate(inflater, container, false);
        rootView = binding.getRoot();
        configureRecycler();
        setEventListeners();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (order != null) {
            binding.textOrderid.setText("Order ID: " + String.valueOf(order.getOrderId()));
            binding.btnCheckoutUpdate.setText("Update");
        } else {
            binding.textOrderid.setVisibility(View.GONE);
            binding.btnCheckoutUpdate.setText("Check Out");
        }
        setTotalValues();
        super.onViewCreated(view, savedInstanceState);
    }

    private void getCartData() {
        cart = DataPersistence.getCartSF(getContext());
    }

    private void configureRecycler() {
        // Create recycler view
        RecyclerView recycler = binding.pizzaOrderRecyclerView;
        // Set the default layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(layoutManager);
        // Create list adapter
        if (order == null) {
            adapter = new OrderSummaryAdapter(cart.getPizzaList(), this);
        } else {
            adapter = new OrderSummaryAdapter(order.getPizzaList(), this);
        }
        recycler.setAdapter(adapter);
    }

    private void setEventListeners() {
        binding.btnCheckoutUpdate.setOnClickListener(onClickCheckOutUpdate);
    }

    private void setTotalValues() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        if (order == null) {
            binding.textSubtotal.setText(currency.format(cart.getSubTotal()));
            binding.textTax.setText(currency.format(cart.getTax()));
            binding.textTotal.setText(currency.format(cart.getTotal()));
        } else {
            binding.textSubtotal.setText(currency.format(order.getSubTotal()));
            binding.textTax.setText(currency.format(order.getTax()));
            binding.textTotal.setText(currency.format(order.getTotal()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void edit(int position) {
        // pass pizza object and navigate to next view
        if (order == null) {
            Navigation.findNavController(rootView)
                    .navigate(OrderSummaryFragmentDirections
                            .actionNavOrderSummaryToCustomizePizza(DisplayMode.EDIT_PIZZA_CART, cart, null, position));
        } else {
            Navigation.findNavController(rootView)
                    .navigate(OrderSummaryFragmentDirections
                            .actionNavOrderSummaryToCustomizePizza(DisplayMode.EDIT_PIZZA_ORDER, null, order, position));
        }
    }

    public void remove(int position) {
        if (order == null) {
            cart.removePizza(position);
            // if cart is empty, set cart object to null and navigate back to home screen
            if (cart.getPizzaList().isEmpty()) {
                DataPersistence.deleteCartSF(getContext());
                Navigation.findNavController(rootView)
                        .navigate(OrderSummaryFragmentDirections.actionOrderSummaryFragmentToNavigationHome());
            } else {
                DataPersistence.saveCartSF(cart, getContext());
            }
        } else {
            order.removePizza(position);
            //Todo: Update order list object in shared preferences
        }
        setTotalValues();
        adapter.notifyItemRemoved(position);
    }
}

//TODO: Hide cart icon from the menu if the screen opened is the cart screen

