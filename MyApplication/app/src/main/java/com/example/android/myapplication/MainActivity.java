package com.example.android.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter mArrayAdapter;
    String[] numbers = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 1; i < 101; i++) {
            numbers[i - 1] = String.valueOf(i);
        }
        ListView listView = findViewById(R.id.lv_numbers);
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, numbers);
        listView.setAdapter(mArrayAdapter);
    }
}
