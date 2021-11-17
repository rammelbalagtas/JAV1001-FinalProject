package com.rammelbalagtas.finalproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.models.Topping.Topping;

import java.util.ArrayList;

public class ToppingsAdapter<T extends Topping> extends RecyclerView.Adapter<ToppingsAdapter<T>.ViewHolder> {

    private final ArrayList<T> toppingList;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param toppingList contains the data to populate views to be used
     *                  by RecyclerView
     */
    public ToppingsAdapter(ArrayList<T> toppingList) {
        this.toppingList = toppingList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ToppingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topping_item, parent, false);
        return new ToppingsAdapter.ViewHolder(view);
    }

    // Bind data to view holder (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ToppingsAdapter<T>.ViewHolder viewholder, int position) {
        Topping topping = toppingList.get(position);
        // Get element from the dataset at this position and replace the
        // contents of the view with that element
        viewholder.getTextToppingName().setText(topping.getName());
        viewholder.getTextToppingLevel().setText(topping.getLevel());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return toppingList == null ? 0 : toppingList.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textToppingName;
        private TextView textToppingLevel;

        public ViewHolder(@NonNull View view) {
            super(view);

            Button btnMoreTopping = view.findViewById(R.id.btn_more_topping);
            Button btnLessTopping = view.findViewById(R.id.btn_less_topping);
            textToppingLevel = view.findViewById(R.id.text_topping_level);
            textToppingName = view.findViewById(R.id.text_topping_name);

            // Define click listener for the add/minus buttons
            btnMoreTopping.setOnClickListener(onClickMoreTopping);
            btnLessTopping.setOnClickListener(onClickLessTopping);
        }

        public TextView getTextToppingName() {
            return textToppingName;
        }

        public TextView getTextToppingLevel() {
            return textToppingLevel;
        }

        private View.OnClickListener onClickMoreTopping = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getLayoutPosition(); //starts with 0
                Topping topping = toppingList.get(position);
                // Get element from the dataset at this position and replace the
                // contents of the view with that element
                String toppingLevel = topping.getLevel();
                topping.setLevel(toppingLevel);
                textToppingLevel.setText(toppingLevel);
            }
        };

        private View.OnClickListener onClickLessTopping = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getLayoutPosition(); //starts with 0
                Topping topping = toppingList.get(position);
                // Get element from the dataset at this position and replace the
                // contents of the view with that element
                String toppingLevel = topping.getLevel();
                topping.setLevel(toppingLevel);
                textToppingLevel.setText(toppingLevel);
            }
        };
    }
}
