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
import com.rammelbalagtas.finalproject.models.Pizza;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {

    private final ArrayList<Pizza> pizzaList;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param pizzaList contains the data to populate views to be used
     *                  by RecyclerView
     */
    public OrderSummaryAdapter(ArrayList<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderSummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pizza_order_item, parent, false);
        return new OrderSummaryAdapter.ViewHolder(view);
    }

    // Bind data to view holder (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.ViewHolder viewholder, int position) {
        Pizza pizza = pizzaList.get(position);
        // Get element from the dataset at this position and replace the
        // contents of the view with that element
        viewholder.getPizzaName().setText(pizza.getName());
        viewholder.getPizzaTopping().setText(pizza.getDescription());
        viewholder.getPizzaQuantity().setText(String.valueOf(pizza.getQuantity()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pizzaList == null ? 0 : pizzaList.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pizzaName;
        private TextView pizzaTopping;
        private TextView pizzaQuantity;

        public ViewHolder(@NonNull View view) {
            super(view);

            pizzaName = view.findViewById(R.id.order_id);
            pizzaTopping = view.findViewById(R.id.pizza_order_topping);
            pizzaQuantity = view.findViewById(R.id.pizza_order_qty);

            // Define click listener for the buttons
            Button btnEditItem = view.findViewById(R.id.btn_view_order);
            btnEditItem.setOnClickListener(onClickEdit);
            Button btnRemoveItem = view.findViewById(R.id.btn_cancel_order);
            btnRemoveItem.setOnClickListener(onClickRemove);
            Button btnAddQty = view.findViewById(R.id.btn_add_qty);
            btnAddQty.setOnClickListener(onClickAddQty);
            Button btnDeductQty = view.findViewById(R.id.btn_deduct_qty);
            btnDeductQty.setOnClickListener(onClickDeductQty);
        }

        public TextView getPizzaName() {
            return pizzaName;
        }

        public TextView getPizzaTopping() {
            return pizzaTopping;
        }

        public TextView getPizzaQuantity() {
            return pizzaQuantity;
        }

        private View.OnClickListener onClickEdit = view -> {
            int position = getLayoutPosition();
            // navigate to next view
            // Navigation.findNavController(view).navigate();
        };

        private View.OnClickListener onClickRemove = view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Remove Order Confirmation")
                    .setMessage("Do you really want to remove the item?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            int position = getLayoutPosition();
                            pizzaList.remove(position);
                            notifyItemRemoved(position);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        };

        private View.OnClickListener onClickAddQty = view -> {
            int position = getLayoutPosition();
        };

        private View.OnClickListener onClickDeductQty = view -> {
            int position = getLayoutPosition();

        };


    }
}
