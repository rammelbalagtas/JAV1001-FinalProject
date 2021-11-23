package com.rammelbalagtas.finalproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.models.Pizza;

import java.util.ArrayList;

public class PizzaListAdapter extends RecyclerView.Adapter<PizzaListAdapter.ViewHolder> {

    private final ArrayList<Pizza> pizzaList;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param pizzaList contains the data to populate views to be used
     *                  by RecyclerView
     */
    public PizzaListAdapter(ArrayList<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.special_pizza_item, parent, false);
        return new ViewHolder(view);
    }

    // Bind data to view holder (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int position) {
        Pizza pizza = pizzaList.get(position);
        // Get element from the dataset at this position and replace the
        // contents of the view with that element
        viewholder.getTextName().setText(pizza.getName());
        viewholder.getTextDescription().setText(pizza.getDescription());
        int imageResourceId;
        switch (position) {
            case 0:
                imageResourceId = R.drawable.pizza1;
                break;
            case 1:
                imageResourceId = R.drawable.pizza2;
                break;
            case 2:
                imageResourceId = R.drawable.pizza3;
                break;
            case 3:
                imageResourceId = R.drawable.pizza4;
                break;
            case 4:
                imageResourceId = R.drawable.pizza5;
                break;
            default:
                imageResourceId = 0;
        }
        viewholder.getPizzaImage().setImageResource(imageResourceId);
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

        private TextView textName;
        private TextView textDescription;
        private ImageView pizzaImage;

        public ViewHolder(@NonNull View view) {
            super(view);

            textName = view.findViewById(R.id.text_special_pizza_name);
            textDescription = view.findViewById(R.id.text_special_pizza_description);
            pizzaImage = view.findViewById(R.id.special_pizza_item_image);

            // Define click listener for the ViewHolder's View
            view.setOnClickListener(onClickPizza);
        }

        public TextView getTextName() {
            return textName;
        }
        public TextView getTextDescription() {
            return textDescription;
        }
        public ImageView getPizzaImage() { return pizzaImage; }

        private View.OnClickListener onClickPizza = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getLayoutPosition();
                // navigate to next view
                Navigation.findNavController(view).navigate(R.id.nav_specialpizza_to_customizepizza);
            }
        };
    }
}
