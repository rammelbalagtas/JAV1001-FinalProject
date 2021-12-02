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
import com.rammelbalagtas.finalproject.helper.PizzaDataConfiguration;
import com.rammelbalagtas.finalproject.models.Pizza;
import com.rammelbalagtas.finalproject.ui.order_summary.IOrderSummary;

import java.text.NumberFormat;
import java.util.ArrayList;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {

    private final ArrayList<Pizza> pizzaList;
    private IOrderSummary delegate;

    public OrderSummaryAdapter(ArrayList<Pizza> pizzaList, IOrderSummary delegate) {
        this.pizzaList = pizzaList;
        this.delegate = delegate;
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
        // Get element from the dataset at this position and replace the
        // contents of the view with that element
        Pizza pizza = pizzaList.get(position);
        viewholder.getPizzaName().setText(pizza.getName());
        viewholder.getPizzaTopping().setText(PizzaDataConfiguration.buildToppingDescription(pizza));
        viewholder.getPizzaQuantity().setText("Quantity: " + String.valueOf(pizza.getQuantity()));
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        viewholder.getPizzaTotalPrice().setText(currency.format(pizza.getPrice() * pizza.getQuantity()));
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

        private final TextView pizzaName;
        private final TextView pizzaTopping;
        private final TextView pizzaQuantity;
        private final TextView pizzaTotalPrice;

        public ViewHolder(@NonNull View view) {
            super(view);

            pizzaName = view.findViewById(R.id.order_id);
            pizzaTopping = view.findViewById(R.id.topping_description);
            pizzaQuantity = view.findViewById(R.id.pizza_order_qty);
            pizzaTotalPrice = view.findViewById(R.id.pizza_price_total);

            // Define click listener for the buttons
            Button btnEditItem = view.findViewById(R.id.btn_view_order);
            btnEditItem.setOnClickListener(onClickEdit);
            Button btnRemoveItem = view.findViewById(R.id.btn_cancel_order);
            btnRemoveItem.setOnClickListener(onClickRemove);
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

        public TextView getPizzaTotalPrice() { return pizzaTotalPrice; }

        private final View.OnClickListener onClickEdit = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.edit(getLayoutPosition());
            }
        };

        private final View.OnClickListener onClickRemove = view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Remove Order Confirmation")
                    .setMessage("Do you really want to remove the item?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            delegate.remove(getLayoutPosition());
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        };

    }
}
