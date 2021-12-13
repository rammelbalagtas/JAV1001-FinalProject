package com.rammelbalagtas.finalproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.rammelbalagtas.finalproject.helper.DataPersistence;
import com.rammelbalagtas.finalproject.helper.DisplayMode;
import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.databinding.FragmentHomeBinding;
import com.rammelbalagtas.finalproject.helper.PizzaDataConfiguration;
import com.rammelbalagtas.finalproject.models.Cart;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Enabled options menu
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Attach listeners to events
        ImageView customPizza = binding.imageCustomPizza;
        customPizza.setOnClickListener(onClickCustomPizza);
        ImageView specialPizza = binding.imageSpecialPizza;
        specialPizza.setOnClickListener(onClickSpecialPizza);
        return root;
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

    /**
     * custom pizza onClick listener
     */
    private final View.OnClickListener onClickCustomPizza = view -> Navigation.findNavController(view)
            .navigate(HomeFragmentDirections.
                    actionNavHomeToCustomizePizza(getResources().getString(R.string.build_your_own), DisplayMode.NEW, (float) PizzaDataConfiguration.pizzaBasePrice));

    /**
     * special pizza onClick listener
     */
    private final View.OnClickListener onClickSpecialPizza = view ->
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_special_pizza);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}