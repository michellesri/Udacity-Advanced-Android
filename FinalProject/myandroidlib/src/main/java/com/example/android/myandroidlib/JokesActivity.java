package com.example.android.myandroidlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        Intent intent = getIntent();
        String joke = intent.getStringExtra("joke");
        TextView jokeTv = findViewById(R.id.joke_tv);
        jokeTv.setText(joke);

    }
}
