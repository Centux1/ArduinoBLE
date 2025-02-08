package com.example.arduinoble;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    public static GATT_BLE BLeGATT;

    Button btn_numpad, btn_terminal, btn_switch, btn_disc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_numpad = findViewById(R.id.btn_numpad);
        btn_terminal = findViewById(R.id.btn_terminal);
        btn_switch = findViewById(R.id.btn_switch);

        btn_disc = findViewById(R.id.btn_disconnect);

        btn_numpad.setOnClickListener(this);
        btn_terminal.setOnClickListener(this);
        btn_switch.setOnClickListener(this);

        btn_disc.setOnClickListener(v -> BLeGATT.disconnect());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btn_numpad.getId()) {
            startActivity(new Intent(this, NumpadActivity.class));
        }
        else if (v.getId() == btn_switch.getId()) {
           startActivity(new Intent(this, SwitchActivity.class));
       }
    }
}