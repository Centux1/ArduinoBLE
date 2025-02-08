package com.example.arduinoble;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NumpadActivity extends AppCompatActivity {

    private EditText numpadDisplay;
    private GATT_BLE mBLeGATT;
    private final StringBuilder data = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numpad);

        mBLeGATT = MenuActivity.BLeGATT;

        numpadDisplay = findViewById(R.id.numpad_display);
        Button btnDel = findViewById(R.id.btnDel);
        Button btnConf = findViewById(R.id.btnConf);
        
        Button btn_back = findViewById(R.id.btn_back);

        btnDel.setOnClickListener(v -> {
            data.setLength(0);
            numpadDisplay.setText("");
        });

        btnConf.setOnClickListener(v -> {
            mBLeGATT.sendMessage(data.toString());
            data.setLength(0);
            numpadDisplay.setText("");
        });

        int[] buttonIds = {
                R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btn0
        };

        for (int id : buttonIds) {
            Button numberButton = findViewById(id);
            numberButton.setOnClickListener(v -> {
                data.append(((Button) v).getText().toString());
                numpadDisplay.setText(data.toString());
            });
        }

        btn_back.setOnClickListener(v -> {
            data.setLength(0);
            startActivity(new Intent(this, MenuActivity.class));
        });
    }
}