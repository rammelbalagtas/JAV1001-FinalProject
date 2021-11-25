package com.rammelbalagtas.finalproject.ui.order_summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.adapter.OrderSummaryAdapter;

import com.rammelbalagtas.finalproject.databinding.FragmentOrderSummaryBinding;
import com.rammelbalagtas.finalproject.helper.DataPersistence;
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
            order = new Order(1);
            order.setPizzaList(cart.getPizzaList());
            order.setSubTotal(cart.getSubTotal());
            order.setTax(cart.getTax());
            order.setTotal(cart.getTotal());
            order.setOrderStatus("In Progress");
            OrderList orderList = DataPersistence.getOrderListSF(getContext());
            orderList.addOrder(order);
            DataPersistence.saveOrderListSF(orderList, getContext());
            //TODO: Add logic to navigate to home
            //TODO: Add logic to empty out the cart
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
        updateTotalValues();
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

    private void updateTotalValues() {
        String total, tax, subTotal;
        if (order == null) {

        } else {

        }
        binding.textSubtotal.setText("$100");
        binding.textTax.setText("$100");
        binding.textTotal.setText("$100");
        binding.textOrderid.setText("Order ID XXXX");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void remove(int position) {
        if (order == null) {
            cart.removePizza(position);
            DataPersistence.saveCartSF(cart, getContext());
        } else {
            order.removePizza(position);
            //Todo: Update order list object in shared preferences
        }
        updateTotalValues();
        adapter.notifyItemRemoved(position);
    }
}
