package com.example.arduinoble;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;

import androidx.annotation.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class GATT_BLE {
    private final MainActivity main;
    private final BluetoothDevice device;

    private BluetoothGatt gatt;
    private BluetoothGattCharacteristic characteristic;

    private static final UUID SERVICE_UUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    private static final UUID CHARACTERISTIC_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");

    public GATT_BLE(MainActivity mainActivity, BluetoothDevice device) {
        main = mainActivity;
        this.device = device;
    }

    @SuppressLint("MissingPermission")
    public void connect() {
        gatt =  device.connectGatt(main.getApplicationContext(), true, new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);

                switch (newState) {
                    case BluetoothGatt.STATE_CONNECTED:
                        gatt.discoverServices();
                        break;
                    case BluetoothGatt.STATE_DISCONNECTED:
                        disconnect();
                        break;
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                super.onServicesDiscovered(gatt, status);

                if (status == BluetoothGatt.GATT_SUCCESS) {
                    BluetoothGattService service =  gatt.getService(SERVICE_UUID);
                    if (service == null) return;

                    characteristic =  service.getCharacteristic(CHARACTERISTIC_UUID);
                }
            }

            @Override
            public void onCharacteristicChanged(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] value) {
                super.onCharacteristicChanged(gatt, characteristic, value);

                if (characteristic.getService().getUuid().equals(SERVICE_UUID) && characteristic.getUuid().equals(CHARACTERISTIC_UUID)) {
                    Utils.toast(main.getApplicationContext(), "Text: " + new String(value, StandardCharsets.UTF_8));
                }
            }
        });

        gatt.connect();
    }

    @SuppressLint("MissingPermission")
    public void sendMessage(String message) {
        if (characteristic == null) {
            Utils.toast(main.getApplicationContext(), "Sending failed: no characteristic");
            return;
        }

        characteristic.setValue(message + "$");
        boolean success = gatt.writeCharacteristic(characteristic);

        if (!success) {
            Utils.toast(main.getApplicationContext(), "Sending failed");
        }
    }

    @SuppressLint("MissingPermission")
    public void disconnect() {
        gatt.close();
        characteristic = null;
        main.disconnect();
    }

}
