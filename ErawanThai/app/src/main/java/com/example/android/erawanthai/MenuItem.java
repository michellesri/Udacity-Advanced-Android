package com.example.android.erawanthai;

import java.io.Serializable;
import java.util.List;

public class MenuItem implements Serializable {
    public final String name;
    public final String description;
    public final long price;
    public final List<SpiceLevel> spiceLevelChoices;
    public final List<Meat> meatChoices;
    public final List<Size> sizeChoices;
    public final Category category;

    public MenuItem(String name, String description, long price, List<SpiceLevel> spiceLevelChoices,
                    List<Meat> meatChoices, List<Size> sizeChoices, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.spiceLevelChoices = spiceLevelChoices;
        this.meatChoices = meatChoices;
        this.sizeChoices = sizeChoices;
        this.category = category;
    }

    public enum SpiceLevel {
        MILD,
        MEDIUM,
        HOT,
        THAI_HOT
    }

    public enum Meat {
        CHICKEN,
        BEEF,
        PRAWN,
        CHICKEN_AND_PRAWN,
        CALAMARI,
        PORK,
        VEGETARIAN
    }

    public enum Size {
        BOWL,
        FIRE_POT,
        EXTRA_LARGE_POT
    }

    public enum Category {
        APPETIZER,
        SOUP,
        SALAD,
        ENTREE,
        DESSERT,
        DRINK,
        SIDE
    }
}
