package com.rammelbalagtas.finalproject.ui.order_summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import java.text.NumberFormat;

public class OrderSummaryFragment extends Fragment implements IOrderSummary {

    private Cart cart;
    private Order order;
    private int orderIndex;
    private FragmentOrderSummaryBinding binding;
    private OrderSummaryAdapter adapter;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = OrderSummaryFragmentArgs.fromBundle(getArguments()).getArgOrderObject();
        orderIndex = OrderSummaryFragmentArgs.fromBundle(getArguments()).getArgOrderIndex();
        if (order == null) { //if order object is null, means the user is accessing the Cart
            cart = DataPersistence.getCartSF(requireContext());
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
            binding.textOrderid.setText("Order ID: " + order.getOrderId());
            binding.btnCheckoutUpdate.setText("Update");
        } else {
            binding.textOrderid.setVisibility(View.GONE);
            binding.btnCheckoutUpdate.setText("Check Out");
        }
        setTotalValues();
        super.onViewCreated(view, savedInstanceState);
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
                OrderList orderList = DataPersistence.getOrderListSF(requireContext());
                orderList.addOrder(order);
                DataPersistence.saveOrderListSF(orderList, requireContext());
                DataPersistence.deleteCartSF(requireContext());
                new AlertDialog.Builder(requireContext())
                        .setMessage("Order ID " + order.getOrderId() + " is created.")
                        .setNeutralButton(android.R.string.ok,
                                (dialog, which) ->
                                        Navigation.findNavController(rootView).
                                                navigate(R.id.action_order_summary_fragment_to_navigation_home))
                        .create()
                        .show();
            } else {
                //Update existing order and navigate back to order history screen
                OrderList orderList = DataPersistence.getOrderListSF(requireContext());
                orderList.updateOrder(order, orderIndex);
                DataPersistence.saveOrderListSF(orderList, requireContext());
                Navigation.findNavController(rootView).navigateUp();
            }
        }
    };

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
                DataPersistence.deleteCartSF(requireContext());
                Navigation.findNavController(rootView)
                        .navigate(OrderSummaryFragmentDirections.actionOrderSummaryFragmentToNavigationHome());
            } else {
                DataPersistence.saveCartSF(cart, requireContext());
            }
        } else {
            if (order.getPizzaList().size() == 1) {
                Toast.makeText(requireContext(),
                        "Since there is only one item left, cancel the order from Order History tab.",
                        Toast.LENGTH_LONG).show();
            } else {
                order.removePizza(position);
                OrderList orderList = DataPersistence.getOrderListSF(requireContext());
                orderList.updateOrder(order, orderIndex);
                DataPersistence.saveOrderListSF(orderList, requireContext());
            }
        }
        setTotalValues();
        adapter.notifyItemRemoved(position);
    }
}

