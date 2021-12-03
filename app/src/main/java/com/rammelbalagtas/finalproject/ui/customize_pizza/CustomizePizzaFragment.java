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
import androidx.appcompat.app.AlertDialog;
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
import com.rammelbalagtas.finalproject.models.Order;
import com.rammelbalagtas.finalproject.models.Pizza;
import com.rammelbalagtas.finalproject.helper.PizzaDataConfiguration;
import com.rammelbalagtas.finalproject.models.Topping.Meat;
import com.rammelbalagtas.finalproject.models.Topping.Sauce;
import com.rammelbalagtas.finalproject.models.Topping.Vegetable;

import java.util.Arrays;

public class CustomizePizzaFragment extends Fragment {

    private DisplayMode displayMode;
    private Pizza currentPizza;
    private View rootView;
    private FragmentCustomizePizzaBinding binding;
    private TextView quantityText;
    private Cart cart;
    private Order order;
    private int pizzaIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        displayMode = CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgMode();

        if (displayMode.equals(DisplayMode.NEW)) {
            currentPizza = new Pizza();
            currentPizza.setName(CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgPizzaName());
            currentPizza.setQuantity(1);
            currentPizza.setPrice(CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgPizzaPrice());
            for (String sauce : PizzaDataConfiguration.sauceTopping) {
                currentPizza.getSauceList().add(new Sauce(sauce, "None"));
            }
            for (String meat : PizzaDataConfiguration.meatTopping) {
                currentPizza.getMeatList().add(new Meat(meat, "None"));
            }
            for (String vegetable : PizzaDataConfiguration.vegetableTopping) {
                currentPizza.getVegetableList().add(new Vegetable(vegetable, "None"));
            }
        } else {
            cart = CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgCartObject();
            order = CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgOrderObject();
            pizzaIndex = CustomizePizzaFragmentArgs.fromBundle(getArguments()).getArgPizzaIndex();
            if (order == null) {
                currentPizza = cart.getPizzaList().get(pizzaIndex);
            } else {
                currentPizza = order.getPizzaList().get(pizzaIndex);
            }
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
        quantityText.setText(String.valueOf(currentPizza.getQuantity()));
        Button updateCart = binding.btnAddToCart;
        if (displayMode.equals(DisplayMode.NEW)) {
            updateCart.setText(R.string.add_to_cart_text);
        } else {
            updateCart.setText(R.string.save_text);
        }
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
        sauceRecycler.setAdapter(new ToppingsAdapter<>(currentPizza.getSauceList()));
    }

    private void configureMeatRecycler() {
        // Create recycler view
        RecyclerView meatRecycler = rootView.findViewById(R.id.meat_recycler_view);
        // Set the default layout manager
        LinearLayoutManager meatLayoutManager = new LinearLayoutManager(rootView.getContext());
        meatRecycler.setLayoutManager(meatLayoutManager);
        // Create list adapter
        meatRecycler.setAdapter(new ToppingsAdapter<>(currentPizza.getMeatList()));
    }

    private void configureVegetableRecycler() {
        // Create recycler view
        RecyclerView vegetableRecycler = rootView.findViewById(R.id.veggie_recycler_view);
        // Set the default layout manager
        LinearLayoutManager vegetableLayoutManager = new LinearLayoutManager(rootView.getContext());
        vegetableRecycler.setLayoutManager(vegetableLayoutManager);
        // Create list adapter
        vegetableRecycler.setAdapter(new ToppingsAdapter<>(currentPizza.getVegetableList()));
    }

    private void configureSpinnerElements() {

        //Spinner values for pizza size
        Spinner pizzaSizeSpinner = rootView.findViewById(R.id.spinner_size);
        ArrayAdapter<String> pizzaSizeAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_spinner_item, PizzaDataConfiguration.pizzaSize);
        pizzaSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaSizeSpinner.setAdapter(pizzaSizeAdapter);
        if (!displayMode.equals(DisplayMode.NEW)) {
            pizzaSizeSpinner.setSelection(Arrays.asList(PizzaDataConfiguration.pizzaSize).
                    indexOf(currentPizza.getSize()));
        }
        pizzaSizeSpinner.setOnItemSelectedListener(onSelectSize);

        //Spinner values for pizza crust
        Spinner pizzaCrustSpinner = rootView.findViewById(R.id.spinner_crust);
        ArrayAdapter<String> pizzaCrustAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_spinner_item, PizzaDataConfiguration.pizzaCrust);
        pizzaCrustAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaCrustSpinner.setAdapter(pizzaCrustAdapter);
        if (!displayMode.equals(DisplayMode.NEW)) {
            pizzaCrustSpinner.setSelection(Arrays.asList(PizzaDataConfiguration.pizzaCrust).
                    indexOf(currentPizza.getCrust()));
        }
        pizzaCrustSpinner.setOnItemSelectedListener(onSelectCrust);
    }

    private void setEventListeners() {
        Button btnAddToCart = rootView.findViewById(R.id.btn_add_to_cart);
        btnAddToCart.setOnClickListener(onClickAddSaveOrder);
        Button btnAddPizza = rootView.findViewById(R.id.btn_add_pizza);
        btnAddPizza.setOnClickListener(onClickAddPizza);
        Button btnLessPizza = rootView.findViewById(R.id.btn_less_pizza);
        btnLessPizza.setOnClickListener(onClickLessPizza);
    }

    private final View.OnClickListener onClickAddSaveOrder = view -> {
        if (isDataValid()) {
            if (displayMode.equals(DisplayMode.NEW)) {
                Cart cart = DataPersistence.getCartSF(requireContext());
                cart.addPizza(currentPizza);
                DataPersistence.saveCartSF(cart, requireContext());
                new AlertDialog.Builder(requireContext())
                        .setMessage("You item has been added to the cart")
                        .setNeutralButton(android.R.string.ok,
                                (dialog, which) ->
                                        Navigation.findNavController(rootView).
                                                navigate(R.id.action_nav_customize_pizza_to_home))
                        .create()
                        .show();
            } else {
                if (displayMode.equals(DisplayMode.EDIT_PIZZA_CART)) {
                    DataPersistence.saveCartSF(cart, requireContext());
                }
                Navigation.findNavController(rootView).navigateUp();
            }
        } else {
            return;
        }
    };

    private boolean isDataValid() {
        boolean hasSauce = false, hasMeat = false, hasVegetable = false;
        for (Sauce sauce : currentPizza.getSauceList()) {
            if (!sauce.getLevel().equals("None")) {
                hasSauce = true;
                break;
            }
        }
        if (!hasSauce) {
            Toast.makeText(getContext(), "Please add at least one sauce", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            for (Meat meat : currentPizza.getMeatList()) {
                if (!meat.getLevel().equals("None")) {
                    hasMeat = true;
                    break;
                }
            }
            if (!hasMeat) {
                for (Vegetable vegetable : currentPizza.getVegetableList()) {
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

