package com.example.arduinoble;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private HashMap<String, BLE_Device> BLEDevicesHashMap;
    private ArrayList<BLE_Device> BLEDevicesArrayList;
    private ListAdapter_BLE_Devices adapter;
    private Button btn_scan;
    private CheckBox show_all;

    private Scanner_BLE BLEScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BLEScanner = new Scanner_BLE(this, 5000, -75);

        BLEDevicesHashMap = new HashMap<>();
        BLEDevicesArrayList = new ArrayList<>();

        adapter = new ListAdapter_BLE_Devices(this, R.layout.device_list_item, BLEDevicesArrayList);

        ListView listView = new ListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        btn_scan = findViewById(R.id.btn_scan);
        ((ScrollView) findViewById(R.id.scrollView)).addView(listView);
        
        btn_scan.setOnClickListener(this);

        show_all = findViewById(R.id.show_all);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        stopScan();

        BLE_Device device = BLEDevicesArrayList.get(position);

        GATT_BLE BLeGATT = new GATT_BLE(this, device.getDevice());
        BLeGATT.connect();
        MenuActivity.BLeGATT = BLeGATT;

        startActivity(new Intent(this, MenuActivity.class));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_scan) {
            if (!BLEScanner.isScanning()) {
                startScan();
            } else {
                stopScan();
            }
        }
    }

    public void addDevice(BluetoothDevice device, int rssi) {
        String address = device.getAddress();
        if (!BLEDevicesHashMap.containsKey(address)) {
            BLE_Device bleDevice = new BLE_Device(device);
            bleDevice.setRSSI(rssi);

            if (!show_all.isChecked()) {
                if (bleDevice.getName() == null || bleDevice.getName().isEmpty()) {
                    return;
                }
            }

            BLEDevicesHashMap.put(address, bleDevice);
            BLEDevicesArrayList.add(bleDevice);
        }
        else {
            Objects.requireNonNull(BLEDevicesHashMap.get(address)).setRSSI(rssi);
        }

        adapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    public void startScan(){
        btn_scan.setText("Scanning...");

        BLEDevicesArrayList.clear();
        BLEDevicesHashMap.clear();

        BLEScanner.start();
    }

    @SuppressLint("SetTextI18n")
    public void stopScan() {
        btn_scan.setText("Scan Again");
        BLEScanner.stop();
    }

    public void disconnect() {
        startActivity(new Intent(this, MainActivity.class));
    }
}