package com.example.arduinoble;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;


public class SwitchActivity extends AppCompatActivity  {

    SwitchCompat switch1;
    Button btn_back;


    GATT_BLE mBLeGATT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        btn_back = findViewById(R.id.btn_back2);

        switch1 = findViewById(R.id.switch_switch);

        mBLeGATT = MenuActivity.BLeGATT;

        switch1.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            mBLeGATT.sendMessage(isChecked ? "1" : "0");
        }));


        btn_back.setOnClickListener(v -> {
            startActivity(new Intent(this, MenuActivity.class));
        });

    }
}