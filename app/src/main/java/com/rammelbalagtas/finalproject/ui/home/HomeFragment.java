package com.rammelbalagtas.finalproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.rammelbalagtas.finalproject.helper.DisplayMode;
import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.databinding.FragmentHomeBinding;
import com.rammelbalagtas.finalproject.helper.PizzaDataConfiguration;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

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

    /**
     * custom pizza onClick listener
     */
    private final View.OnClickListener onClickCustomPizza = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view)
                    .navigate(HomeFragmentDirections.
                            actionNavHomeToCustomizePizza(getResources().getString(R.string.build_your_own), DisplayMode.NEW, (float) PizzaDataConfiguration.pizzaBasePrice));
        }
    };

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