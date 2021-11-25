package com.rammelbalagtas.finalproject.ui.customize_pizza;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rammelbalagtas.finalproject.helper.DataPersistence;
import com.rammelbalagtas.finalproject.helper.DisplayMode;
import com.rammelbalagtas.finalproject.R;
import com.rammelbalagtas.finalproject.adapter.ToppingsAdapter;
import com.rammelbalagtas.finalproject.databinding.FragmentCustomizePizzaBinding;
import com.rammelbalagtas.finalproject.models.Cart;
import com.rammelbalagtas.finalproject.models.Pizza;
import com.rammelbalagtas.finalproject.helper.PizzaDataConfiguration;
import com.rammelbalagtas.finalproject.models.Topping.Meat;
import com.rammelbalagtas.finalproject.models.Topping.Sauce;
import com.rammelbalagtas.finalproject.models.Topping.Vegetable;
import com.rammelbalagtas.finalproject.ui.home.HomeFragmentDirections;

import java.util.ArrayList;

public class CustomizePizzaFragment extends Fragment {

    private DisplayMode displayMode;
    private int orderId;
    private Pizza currentPizza;

    private ToppingsAdapter<Sauce> sauceListAdapter;
    private ArrayList<Sauce> sauceList = new ArrayList<>();
    private View rootView;

    private ToppingsAdapter<Meat> meatListAdapter;
    private ArrayList<Meat> meatList = new ArrayList<>();

    private ToppingsAdapter<Vegetable> vegetableListAdapter;
    private ArrayList<Vegetable> vegetableList = new ArrayList<>();

    private FragmentCustomizePizzaBinding binding;
    private TextView quantityText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server
        currentPizza = new Pizza();
        currentPizza.setName(CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgPizzaName());
        displayMode = CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgMode();

        for (String sauce : PizzaDataConfiguration.sauceTopping) {
            sauceList.add(new Sauce(sauce, "None"));
        }
        for (String meat : PizzaDataConfiguration.meatTopping) {
            meatList.add(new Meat(meat, "None"));
        }
        for (String vegetable : PizzaDataConfiguration.vegetableTopping) {
            vegetableList.add(new Vegetable(vegetable, "None"));
        }

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        quantityText = binding.textPizzaName;
        binding.textPizzaName.setText(currentPizza.getName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void configureSauceRecycler() {
        // Create recycler view
        RecyclerView sauceRecycler = rootView.findViewById(R.id.sauce_recycler_view);
        // Set the default layout manager
        LinearLayoutManager sauceLayoutManager = new LinearLayoutManager(rootView.getContext());
        sauceRecycler.setLayoutManager(sauceLayoutManager);
        // Create list adapter
        sauceListAdapter = new ToppingsAdapter<Sauce>(sauceList);
        sauceRecycler.setAdapter(sauceListAdapter);
    }

    private void configureMeatRecycler() {
        // Create recycler view
        RecyclerView meatRecycler = rootView.findViewById(R.id.meat_recycler_view);
        // Set the default layout manager
        LinearLayoutManager meatLayoutManager = new LinearLayoutManager(rootView.getContext());
        meatRecycler.setLayoutManager(meatLayoutManager);
        // Create list adapter
        meatListAdapter = new ToppingsAdapter<Meat>(meatList);
        meatRecycler.setAdapter(meatListAdapter);
    }

    private void configureVegetableRecycler() {
        // Create recycler view
        RecyclerView vegetableRecycler = rootView.findViewById(R.id.veggie_recycler_view);
        // Set the default layout manager
        LinearLayoutManager vegetableLayoutManager = new LinearLayoutManager(rootView.getContext());
        vegetableRecycler.setLayoutManager(vegetableLayoutManager);
        // Create list adapter
        vegetableListAdapter = new ToppingsAdapter<Vegetable>(vegetableList);
        vegetableRecycler.setAdapter(vegetableListAdapter);
    }

    private void configureSpinnerElements() {

        //Spinner values for pizza size
        Spinner pizzaSizeSpinner = rootView.findViewById(R.id.spinner_size);
        ArrayAdapter<String> pizzaSizeAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_spinner_item, PizzaDataConfiguration.pizzaSize);
        pizzaSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaSizeSpinner.setAdapter(pizzaSizeAdapter);
        pizzaSizeSpinner.setOnItemSelectedListener(onSelectSize);

        //Spinner values for pizza crust
        Spinner pizzaCrustSpinner = rootView.findViewById(R.id.spinner_crust);
        ArrayAdapter<String> pizzaCrustAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_spinner_item, PizzaDataConfiguration.pizzaCrust);
        pizzaCrustAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaCrustSpinner.setAdapter(pizzaCrustAdapter);
        pizzaCrustSpinner.setOnItemSelectedListener(onSelectCrust);
    }

    private void setEventListeners() {
        Button btnAddToCart = rootView.findViewById(R.id.btn_add_to_cart);
        btnAddToCart.setOnClickListener(onClickAddToCart);
        Button btnAddPizza = rootView.findViewById(R.id.btn_add_pizza);
        btnAddPizza.setOnClickListener(onClickAddPizza);
        Button btnLessPizza = rootView.findViewById(R.id.btn_less_pizza);
        btnLessPizza.setOnClickListener(onClickLessPizza);
    }

    private final View.OnClickListener onClickAddToCart = view -> {

        boolean isDataValid = validateData();
        if (isDataValid) {
            currentPizza.setSauceList(sauceList);
            currentPizza.setMeatList(meatList);
            currentPizza.setVegetableList(vegetableList);
            currentPizza.setPrice(100.00);
            Cart cart = DataPersistence.getCartSF(getContext());
            cart.addPizza(currentPizza);
            DataPersistence.saveCartSF(cart, getContext());
            Navigation.findNavController(view).navigate(R.id.action_nav_customize_pizza_to_home);
        } else {
            return;
        }
    };

    private boolean validateData() {
        return true;
    }

    private final View.OnClickListener onClickAddPizza = view -> {
        int quantity = currentPizza.getQuantity();
        if (quantity == 10) {

        } else {
            quantity++;
            currentPizza.setQuantity(quantity);
            quantityText.setText(String.valueOf(quantity));
        }
    };

    // Event listener for decrease quantity
    private final View.OnClickListener onClickLessPizza = view -> {
        int quantity = currentPizza.getQuantity();
        if (quantity == 0) {

        } else {
            quantity--;
            currentPizza.setQuantity(quantity);
            quantityText.setText(String.valueOf(quantity));
        }
    };

    // Event listener when size is selected
    private final AdapterView.OnItemSelectedListener onSelectSize = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            currentPizza.setSize(PizzaDataConfiguration.pizzaSize[position]);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    // Event listener when size is selected
    private final AdapterView.OnItemSelectedListener onSelectCrust = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            currentPizza.setCrust(PizzaDataConfiguration.pizzaCrust[position]);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
