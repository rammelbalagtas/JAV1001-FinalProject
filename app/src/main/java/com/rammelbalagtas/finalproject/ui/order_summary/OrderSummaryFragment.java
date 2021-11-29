package com.rammelbalagtas.finalproject.ui.order_summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rammelbalagtas.finalproject.adapter.OrderSummaryAdapter;

import com.rammelbalagtas.finalproject.databinding.FragmentOrderSummaryBinding;
import com.rammelbalagtas.finalproject.helper.DataPersistence;
import com.rammelbalagtas.finalproject.helper.DisplayMode;
import com.rammelbalagtas.finalproject.helper.PizzaDataConfiguration;
import com.rammelbalagtas.finalproject.models.Cart;
import com.rammelbalagtas.finalproject.models.Order;
import com.rammelbalagtas.finalproject.models.OrderList;

public class OrderSummaryFragment extends Fragment implements IOrderSummary {

    private Cart cart;
    private Order order;
    private FragmentOrderSummaryBinding binding;
    private OrderSummaryAdapter adapter;
    private View rootView;

    private final View.OnClickListener onClickCheckOut = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            order = new Order(PizzaDataConfiguration.generateOrderId());
            order.setPizzaList(cart.getPizzaList());
            order.setSubTotal(cart.getSubTotal());
            order.setTax(cart.getTax());
            order.setTotal(cart.getTotal());
            order.setStatus("In Progress");
            OrderList orderList = DataPersistence.getOrderListSF(getContext());
            orderList.addOrder(order);
            DataPersistence.saveOrderListSF(orderList, getContext());
            //TODO: Add logic to navigate to home
            cart = null;
            DataPersistence.deleteCartSF(getContext());
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
            binding.btnCheckout.setText("Update");
        } else {
            binding.textOrderid.setVisibility(View.GONE);
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
        Button checkout = binding.btnCheckout;
        checkout.setOnClickListener(onClickCheckOut);
    }

    private void setTotalValues() {
        String total, tax, subTotal;
        if (order == null) {
            binding.textSubtotal.setText(String.valueOf(cart.getSubTotal()));
            binding.textTax.setText(String.valueOf(cart.getTax()));
            binding.textTotal.setText(String.valueOf(cart.getTotal()));
        } else {
            binding.textSubtotal.setText(String.valueOf(order.getSubTotal()));
            binding.textTax.setText(String.valueOf(order.getTax()));
            binding.textTotal.setText(String.valueOf(order.getTotal()));
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
                    .navigate(OrderSummaryFragmentDirections.actionNavOrderSummaryToCustomizePizza(DisplayMode.EDIT_PIZZA_CART, cart.getPizzaList().get(position)));
        } else {
            Navigation.findNavController(rootView)
                    .navigate(OrderSummaryFragmentDirections.actionNavOrderSummaryToCustomizePizza(DisplayMode.EDIT_PIZZA_ORDER, order.getPizzaList().get(position)));
        }
    }

    public void remove(int position) {
        if (order == null) {
            cart.removePizza(position);
            DataPersistence.saveCartSF(cart, getContext());
        } else {
            order.removePizza(position);
            //Todo: Update order list object in shared preferences
        }
        setTotalValues();
        adapter.notifyItemRemoved(position);
    }
}

//TOD0: Generate order id instead of hardcoding
//TODO: Hide order id field if opening the cart
//TODO: Implement events for increase and decrease quantity or remove the buttons and make quantity static
//TODO: When there is only one item in cart and user click remove, the screen should go back to main screen
//TODO: Button should have dynamic text, if cart is opened, set to CHECKOUT,if order is open, set to UPDATE
//TODO: Hide cart icon from the menu if the screen opened is the cart screen
//TODO: Add topping description and amount per pizza in the screen

