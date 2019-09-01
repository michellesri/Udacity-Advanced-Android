package com.example.android.erawanthai;

import android.view.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuItemMocks {
    public static List<MenuItem> generateMenuItems() {

        List<MenuItem> menuItems = new ArrayList<>();
        List<MenuItem.SpiceLevel> spiceLevels = Arrays.asList(MenuItem.SpiceLevel.values());

         MenuItem koongTod = new MenuItem(
                "Koong Tod",
                "lightly battered fried shrimp",
                995,
                null,
                null,
                null,
                MenuItem.Category.APPETIZER
        );
        menuItems.add(koongTod);

        List<MenuItem.Meat> tomYumMeatChoices = new ArrayList<>();
        tomYumMeatChoices.add(MenuItem.Meat.CHICKEN);
        tomYumMeatChoices.add(MenuItem.Meat.PRAWN);

        List<MenuItem.Size> tomYumSizeChoices = new ArrayList<>();
        tomYumSizeChoices.add(MenuItem.Size.BOWL);
        tomYumSizeChoices.add(MenuItem.Size.FIRE_POT);
        tomYumSizeChoices.add(MenuItem.Size.EXTRA_LARGE_POT);

        MenuItem tomYum = new MenuItem(
                "Tom Yum",
                "flavorful sweet and sour soup",
                1395,
                spiceLevels,
                tomYumMeatChoices,
                tomYumSizeChoices,
                MenuItem.Category.SOUP
        );
        menuItems.add(tomYum);

        MenuItem thaiSalad = new MenuItem(
                "Thai Salad",
                "light salad",
                895,
                null,
                null,
                null,
                MenuItem.Category.SALAD
        );
        menuItems.add(thaiSalad);

        List<MenuItem.Meat> padThaiMeatChoices = Arrays.asList(MenuItem.Meat.CHICKEN, MenuItem.Meat.CHICKEN_AND_PRAWN, MenuItem.Meat.PRAWN);
        MenuItem padThai = new MenuItem(
                "Pad Thai",
                "traditional noodle dish",
                1295,
                spiceLevels,
                padThaiMeatChoices,
                new ArrayList<MenuItem.Size>(),
                MenuItem.Category.ENTREE
        );
        menuItems.add(padThai);

        MenuItem stickyRice = new MenuItem(
                "Sweet Sticky Rice",
                "Sweet sticky rice served with coconut cream sauce",
                500,
                null,
                null,
                null,
                MenuItem.Category.DESSERT
        );
        menuItems.add(stickyRice);


        MenuItem stickyRiceMango = new MenuItem(
                "Sticky Rice with Mango",
                "Sweet coconut sticky rice served with mango on the side",
                695,
                null,
                null,
                null,
                MenuItem.Category.DESSERT
        );
        menuItems.add(stickyRiceMango);

        MenuItem singhaBeer = new MenuItem(
                "Singha Beer",
                "famous Thai beer produced by the Singha family",
                500,
                null,
                null,
                null,
                MenuItem.Category.DRINK
        );
        menuItems.add(singhaBeer);

        MenuItem whiteRice = new MenuItem(
                "White Rice",
                "steamed white rice",
                200,
                null,
                null,
                null,
                MenuItem.Category.SIDE
        );
        menuItems.add(whiteRice);

        MenuItem brownRice = new MenuItem(
                "Brown Rice",
                "steamed brown rice",
                250,
                null,
                null,
                null,
                MenuItem.Category.SIDE
        );
        menuItems.add(brownRice);

        return menuItems;
    }
}
