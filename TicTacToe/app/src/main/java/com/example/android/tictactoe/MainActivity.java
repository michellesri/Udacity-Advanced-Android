package com.example.android.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean isX = true;

    private Button playBtn;

    private Button btn0_0;
    private Button btn0_1;
    private Button btn0_2;
    private Button btn1_0;
    private Button btn1_1;
    private Button btn1_2;
    private Button btn2_0;
    private Button btn2_1;
    private Button btn2_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = findViewById(R.id.play_btn);

        btn0_0 = findViewById(R.id.btn0_0);
        btn0_1 = findViewById(R.id.btn0_1);
        btn0_2 = findViewById(R.id.btn0_2);
        btn1_0 = findViewById(R.id.btn1_0);
        btn1_1 = findViewById(R.id.btn1_1);
        btn1_2 = findViewById(R.id.btn1_2);
        btn2_0 = findViewById(R.id.btn2_0);
        btn2_1 = findViewById(R.id.btn2_1);
        btn2_2 = findViewById(R.id.btn2_2);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });


        btn0_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick((Button) v);

            }
        });
        btn0_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick((Button) v);

            }
        });
        btn0_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick((Button) v);

            }
        });
        btn1_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick((Button) v);

            }
        });
        btn1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick((Button) v);

            }
        });
        btn1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick((Button) v);

            }
        });
        btn2_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick((Button) v);

            }
        });
        btn2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick((Button) v);

            }
        });
        btn2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick((Button) v);

            }
        });

    }

    private void handleClick(Button button) {
        if (isX) {
            button.setText("x");
        } else {
            button.setText("o");
        }
        isX = !isX;
        button.setEnabled(false);

        String gameEnd = winOrLose();
        if(gameEnd != null) {
            enableButtons(false);
            Toast.makeText(this, gameEnd, Toast.LENGTH_LONG).show();
        }
    }


    private String winOrLose() {
        CharSequence text0_0 = btn0_0.getText();
        CharSequence text0_1 = btn0_1.getText();
        CharSequence text0_2 = btn0_2.getText();

        CharSequence text1_0 = btn1_0.getText();
        CharSequence text1_1 = btn1_1.getText();
        CharSequence text1_2 = btn1_2.getText();

        CharSequence text2_0 = btn2_0.getText();
        CharSequence text2_1 = btn2_1.getText();
        CharSequence text2_2 = btn2_2.getText();

        if (text0_0.equals(text0_1) && text0_1.equals(text0_2) && text0_2.length() != 0) {
            return text0_0 + " wins!";
        }

        return null;
    }

    private void enableButtons(boolean isEnabled) {
        btn0_0.setEnabled(isEnabled);
        btn0_1.setEnabled(isEnabled);
        btn0_2.setEnabled(isEnabled);
        btn1_0.setEnabled(isEnabled);
        btn1_1.setEnabled(isEnabled);
        btn1_2.setEnabled(isEnabled);
        btn2_0.setEnabled(isEnabled);
        btn2_1.setEnabled(isEnabled);
        btn2_2.setEnabled(isEnabled);
    }

    private void clearButtonText() {
        btn0_0.setText(null);
        btn0_1.setText(null);
        btn0_2.setText(null);
        btn1_0.setText(null);
        btn1_1.setText(null);
        btn1_2.setText(null);
        btn2_0.setText(null);
        btn2_1.setText(null);
        btn2_2.setText(null);

    }

    private void play() {
        clearButtonText();
        isX = true;
        enableButtons(true);

    }





}
