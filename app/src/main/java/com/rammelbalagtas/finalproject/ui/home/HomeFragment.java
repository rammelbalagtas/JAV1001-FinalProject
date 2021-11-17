package com.rammelbalagtas.finalproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Attach listeners to events
        ImageView customPizza = root.findViewById(R.id.image_custom_pizza);
        customPizza.setOnClickListener(onClickCustomPizza);
        ImageView specialPizza = root.findViewById(R.id.image_special_pizza);
        specialPizza.setOnClickListener(onClickSpecialPizza);
        return root;
    }

    /**
     * custom pizza onClick listener
     */
    private View.OnClickListener onClickCustomPizza = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_blankFragment);
        }
    };

    /**
     * special pizza onClick listener
     */
    private View.OnClickListener onClickSpecialPizza = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.action_to_pizza_list);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}