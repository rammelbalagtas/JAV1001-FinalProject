package com.rammelbalagtas.finalproject.adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.models.Order;
import com.rammelbalagtas.finalproject.ui.order_history.IOrderList;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private final ArrayList<Order> orderList;
    private IOrderList delegate;

    public OrderListAdapter(ArrayList<Order> orderList, IOrderList delegate) {
        this.orderList = orderList;
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);
        return new OrderListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int position) {
        Order order = orderList.get(position);
        // Get element from the dataset at this position and replace the
        // contents of the view with that element
        viewholder.getOrderId().setText("Order ID: " + String.valueOf(order.getOrderId()));
    }

    @Override
    public int getItemCount() {
        return orderList == null ? 0 : orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView orderId;

        public ViewHolder(@NonNull View view) {
            super(view);

            orderId = view.findViewById(R.id.pizza_name);

            // Define click listener for the buttons
            Button btnViewItem = view.findViewById(R.id.btn_view_order);
            btnViewItem.setOnClickListener(onClickView);
            Button btnRemoveItem = view.findViewById(R.id.btn_cancel_order);
            btnRemoveItem.setOnClickListener(onClickCancel);
        }

        private View.OnClickListener onClickView = view -> {
            delegate.edit(getLayoutPosition());
        };

        private View.OnClickListener onClickCancel = view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Remove Order Confirmation")
                    .setMessage("Do you really want to cancel the order?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            delegate.remove(getLayoutPosition());
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        };

        public TextView getOrderId() {
            return orderId;
        }
    }
}
