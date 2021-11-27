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
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Arrays;

public class CustomizePizzaFragment extends Fragment {

    private DisplayMode displayMode;
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

        displayMode = CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgMode();

        if (displayMode.equals(DisplayMode.NEW)) {
            currentPizza = new Pizza();
            currentPizza.setName(CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgPizzaName());
            currentPizza.setPrice(CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgPizzaPrice());
            for (String sauce : PizzaDataConfiguration.sauceTopping) {
                sauceList.add(new Sauce(sauce, "None"));
            }
            for (String meat : PizzaDataConfiguration.meatTopping) {
                meatList.add(new Meat(meat, "None"));
            }
            for (String vegetable : PizzaDataConfiguration.vegetableTopping) {
                vegetableList.add(new Vegetable(vegetable, "None"));
            }
        } else {
            currentPizza = CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgPizzaObject();
            sauceList = currentPizza.getSauceList();
            meatList = currentPizza.getMeatList();
            vegetableList = currentPizza.getVegetableList();
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
        quantityText = binding.textPizzaQty;
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

        if (isDataValid()) {
            currentPizza.setSauceList(sauceList);
            currentPizza.setMeatList(meatList);
            currentPizza.setVegetableList(vegetableList);
            Cart cart = DataPersistence.getCartSF(getContext());
            cart.addPizza(currentPizza);
            DataPersistence.saveCartSF(cart, getContext());
            Navigation.findNavController(view).navigate(R.id.action_nav_customize_pizza_to_home);
        } else {
            return;
        }
    };

    private boolean isDataValid() {

        boolean hasSauce = false, hasMeat = false, hasVegetable = false;

        for (Sauce sauce : sauceList) {
            if (!sauce.getLevel().equals("None")) {
                hasSauce = true;
                break;
            }
        }

        if (!hasSauce) {
            Toast.makeText(getContext(), "Please add at least one sauce", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            for (Meat meat : meatList) {
                if (!meat.getLevel().equals("None")) {
                    hasMeat = true;
                    break;
                }
            }
            if (!hasMeat) {
                for (Vegetable vegetable : vegetableList) {
                    if (!vegetable.getLevel().equals("None")) {
                        hasVegetable = true;
                        break;
                    }
                }

                if (!hasVegetable) {
                    Toast.makeText(getContext(), "Please add at lease one meat and/or vegetable", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    private final View.OnClickListener onClickAddPizza = view -> {
        int quantity = currentPizza.getQuantity();
        if (quantity == PizzaDataConfiguration.maxPizzaQuantity) {
            Toast.makeText(getContext(), "Maximum of 10 pizza per order", Toast.LENGTH_SHORT).show();
        } else {
            quantity++;
            currentPizza.setQuantity(quantity);
            quantityText.setText(String.valueOf(quantity));
        }
    };

    // Event listener for decrease quantity
    private final View.OnClickListener onClickLessPizza = view -> {
        int quantity = currentPizza.getQuantity();
        if (quantity == PizzaDataConfiguration.minPizzaQuantity) {
            Toast.makeText(getContext(), "A minimum of 1 pizza item is allowed", Toast.LENGTH_SHORT).show();
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
            if (displayMode.equals(DisplayMode.NEW)) {
                currentPizza.setSize(PizzaDataConfiguration.pizzaSize[position]);
            } else {
                parent.setSelection(Arrays.asList(PizzaDataConfiguration.pizzaSize).
                        indexOf(currentPizza.getSize()));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    // Event listener when size is selected
    private final AdapterView.OnItemSelectedListener onSelectCrust = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (displayMode.equals(DisplayMode.NEW)) {
                currentPizza.setCrust(PizzaDataConfiguration.pizzaCrust[position]);
            } else {
                parent.setSelection(Arrays.asList(PizzaDataConfiguration.pizzaCrust).
                        indexOf(currentPizza.getCrust()));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
}

//TODO: Finalize layout for customize pizza screen
//TODO: Dynamically change add to cart button. ADD for new order and UPDATE for existing order
//TODO: Set initial quantity to 1

