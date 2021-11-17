package com.rammelbalagtas.finalproject.ui.customize_pizza;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.adapter.ToppingsAdapter;
import com.rammelbalagtas.finalproject.databinding.FragmentCustomizePizzaBinding;
import com.rammelbalagtas.finalproject.models.Topping.Meat;
import com.rammelbalagtas.finalproject.models.Topping.Sauce;
import com.rammelbalagtas.finalproject.models.Topping.Vegetable;

import java.util.ArrayList;

public class CustomizePizzaFragment extends Fragment {

    private ToppingsAdapter<Sauce> sauceListAdapter;
    private ArrayList<Sauce> sauceList = new ArrayList<>();
    private RecyclerView sauceRecycler;
    private View rootView;

    private ToppingsAdapter<Meat> meatListAdapter;
    private ArrayList<Meat> meatList = new ArrayList<>();
    private RecyclerView meatRecycler;

    private ToppingsAdapter<Vegetable> vegetableListAdapter;
    private ArrayList<Vegetable> vegetableList = new ArrayList<>();
    private RecyclerView vegetableRecycler;

    private FragmentCustomizePizzaBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server
        sauceList.add(new Sauce("Sauce 1", "None"));
        sauceList.add(new Sauce("Sauce 2", "None"));

        meatList.add(new Meat("Meat 1", "None"));
        meatList.add(new Meat("Meat 2", "None"));

        vegetableList.add(new Vegetable("Veg 1", "None"));
        vegetableList.add(new Vegetable("Veg 1", "None"));

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the fragment
        binding = FragmentCustomizePizzaBinding.inflate(inflater, container, false);
        rootView = binding.getRoot();
        configureSauceRecycler();
        configureMeatRecycler();
        configureVegetableRecycler();
        configureSpinnerElements();
        setEventListeners();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void configureSauceRecycler() {
        // Create recycler view
        sauceRecycler = rootView.findViewById(R.id.sauce_recycler_view);
        // Set the default layout manager
        LinearLayoutManager sauceLayoutManager = new LinearLayoutManager(rootView.getContext());
        sauceRecycler.setLayoutManager(sauceLayoutManager);
        // Create list adapter
        sauceListAdapter = new ToppingsAdapter<Sauce>(sauceList);
        sauceRecycler.setAdapter(sauceListAdapter);
    }

    private void configureMeatRecycler() {
        // Create recycler view
        meatRecycler = rootView.findViewById(R.id.meat_recycler_view);
        // Set the default layout manager
        LinearLayoutManager meatLayoutManager = new LinearLayoutManager(rootView.getContext());
        meatRecycler.setLayoutManager(meatLayoutManager);
        // Create list adapter
        meatListAdapter = new ToppingsAdapter<Meat>(meatList);
        meatRecycler.setAdapter(meatListAdapter);
    }

    private void configureVegetableRecycler() {
        // Create recycler view
        vegetableRecycler = rootView.findViewById(R.id.veggie_recycler_view);
        // Set the default layout manager
        LinearLayoutManager vegetableLayoutManager = new LinearLayoutManager(rootView.getContext());
        vegetableRecycler.setLayoutManager(vegetableLayoutManager);
        // Create list adapter
        vegetableListAdapter = new ToppingsAdapter<Vegetable>(vegetableList);
        vegetableRecycler.setAdapter(vegetableListAdapter);
    }

    private void configureSpinnerElements() {
        Spinner pizzaSizeSpinner = rootView.findViewById(R.id.spinner_size);
        ArrayAdapter<CharSequence> pizzaSizeAdapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.pizza_size_array, android.R.layout.simple_spinner_item);
        pizzaSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaSizeSpinner.setAdapter(pizzaSizeAdapter);
        pizzaSizeSpinner.setOnItemSelectedListener(onSelectSize);

        Spinner pizzaCrustSpinner = rootView.findViewById(R.id.spinner_crust);
        ArrayAdapter<CharSequence> pizzaCrustAdapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.pizza_crust_array, android.R.layout.simple_spinner_item);
        pizzaCrustAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaCrustSpinner.setAdapter(pizzaCrustAdapter);
        pizzaCrustSpinner.setOnItemSelectedListener(onSelectCrust);
    }

    private void setEventListeners() {
        Button btnAddToCart = rootView.findViewById(R.id.btn_add_to_cart);
        btnAddToCart.setOnClickListener(onClickAddToCart);
    }

    private View.OnClickListener onClickAddToCart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (sauceRecycler.getVisibility() == View.VISIBLE) {
                sauceRecycler.setVisibility(View.GONE);
            } else {
                sauceRecycler.setVisibility(View.VISIBLE);
            }
        }
    };

    // Event listener when size is selected
    private AdapterView.OnItemSelectedListener onSelectSize = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    // Event listener when size is selected
    private AdapterView.OnItemSelectedListener onSelectCrust = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
