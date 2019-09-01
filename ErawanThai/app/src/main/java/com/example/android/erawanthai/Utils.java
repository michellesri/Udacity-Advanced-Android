package com.example.android.erawanthai;

public class Utils {

    public static String formatPrice(long longPrice) {
        return "$" + longPrice / 100 + "." + longPrice % 100;
    }
}
