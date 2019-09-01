package com.example.android.erawanthai;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.erawanthai.db.CartItem;
import com.example.android.erawanthai.db.CartItemDao;
import com.example.android.erawanthai.db.ErawanDatabase;

import java.time.chrono.Era;

public class MenuItemDetailFragment extends Fragment {

    public static MenuItemDetailFragment newInstance(MenuItem menuItem) {
        MenuItemDetailFragment f = new MenuItemDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("MenuItem", menuItem);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_item_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        MenuItem menuItem = (MenuItem) args.getSerializable("MenuItem");

        configureRadioButtons(view, menuItem);
        configureAddToCartListener(view, menuItem);
        TextView selectedMenuItemTv = view.findViewById(R.id.selected_menu_item);
        TextView descriptionTv = view.findViewById(R.id.description_tv);
        selectedMenuItemTv.setText(menuItem.name);
        descriptionTv.setText(menuItem.description);

    }

    private void configureRadioButtons(View view, MenuItem menuItem) {
        if (menuItem.sizeChoices == null || menuItem.sizeChoices.isEmpty()) {
            view.findViewById(R.id.soup_size_radio_group).setVisibility(View.GONE);
        }
        if (menuItem.spiceLevelChoices == null || menuItem.spiceLevelChoices.isEmpty()) {
            view.findViewById(R.id.spice_level_radio_group).setVisibility(View.GONE);
        }
        if (menuItem.meatChoices == null || menuItem.meatChoices.isEmpty()) {
            view.findViewById(R.id.meat_choice_radio_group).setVisibility(View.GONE);
        }
    }

    private void configureAddToCartListener(final View view, final MenuItem menuItem) {
        Button addToCart = view.findViewById(R.id.add_to_cart_btn);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItem.SpiceLevel spice = null;
                MenuItem.Meat meat = null;
                MenuItem.Size size = null;

                if (isRadioButtonChecked(R.id.mild_btn)) {
                    spice = MenuItem.SpiceLevel.MILD;
                }
                if (isRadioButtonChecked(R.id.medium_btn)) {
                    spice = MenuItem.SpiceLevel.MEDIUM;
                }
                if (isRadioButtonChecked(R.id.hot_btn)) {
                    spice = MenuItem.SpiceLevel.HOT;
                }
                if (isRadioButtonChecked(R.id.thai_hot_btn)) {
                    spice = MenuItem.SpiceLevel.THAI_HOT;
                }
                if (isRadioButtonChecked(R.id.chicken_btn)) {
                    meat = MenuItem.Meat.CHICKEN;
                }
                if (isRadioButtonChecked(R.id.beef_btn)) {
                    meat = MenuItem.Meat.BEEF;
                }
                if (isRadioButtonChecked(R.id.pork_btn)) {
                    meat = MenuItem.Meat.PORK;
                }
                if (isRadioButtonChecked(R.id.prawn_btn)) {
                    meat = MenuItem.Meat.PRAWN;
                }
                if (isRadioButtonChecked(R.id.chicken_and_prawn_btn)) {
                    meat = MenuItem.Meat.CHICKEN_AND_PRAWN;
                }
                if (isRadioButtonChecked(R.id.vegetarian_btn)) {
                    meat = MenuItem.Meat.VEGETARIAN;
                }
                if (isRadioButtonChecked(R.id.bowl_btn)) {
                    size = MenuItem.Size.BOWL;
                }
                if (isRadioButtonChecked(R.id.fire_pot_btn)) {
                    size = MenuItem.Size.FIRE_POT;
                }
                if (isRadioButtonChecked(R.id.extra_large_pot)) {
                    size = MenuItem.Size.EXTRA_LARGE_POT;
                }

                EditText quantityEt = view.findViewById(R.id.quantity);

                if (quantityEt.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter a quantity",
                            Toast.LENGTH_LONG).show();
                } else {
                    String quantityString = quantityEt.getText().toString();
                    int quantity = Integer.parseInt(quantityString);
                    CartItem cartItem = new CartItem(menuItem.name, menuItem.price, spice, meat, size, quantity);
                    new CartItemAsyncTask(((MainActivity) getActivity()).db, cartItem).execute();
                    getActivity().onBackPressed();

                }
            }
        });
    }

    private boolean isRadioButtonChecked(int id) {
        return ((RadioButton) getView().findViewById(id)).isChecked();
    }

    private static class CartItemAsyncTask extends AsyncTask<Void, Void, Void> {
        ErawanDatabase db;
        CartItem cartItem;

        public CartItemAsyncTask(ErawanDatabase db, CartItem cartItem) {
            this.db = db;
            this.cartItem = cartItem;
        }

        @Override
        protected Void doInBackground(Void... params) {
            CartItemDao cartItemDao = db.cartItemDao();
            cartItemDao.insertAll(cartItem);
            return null;
        }
    }

}