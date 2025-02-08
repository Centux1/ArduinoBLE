package com.example.arduinoble;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;

public class BLE_Device {

    private final BluetoothDevice bluetoothDevice;
    private int rssi;

    public BLE_Device(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public BluetoothDevice getDevice() {
        return bluetoothDevice;
    }

    public String getAddress() {
        return bluetoothDevice.getAddress();
    }

    @SuppressLint("MissingPermission")
    public String getName() {
        return bluetoothDevice.getName();
    }

    public void setRSSI(int rssi) {
        this.rssi = rssi;
    }

    public int getRSSI() {
        return rssi;
    }
}