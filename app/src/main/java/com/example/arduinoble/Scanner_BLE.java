package com.example.arduinoble;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Handler;
import java.util.List;

@SuppressLint("MissingPermission")
public class Scanner_BLE {

    private final MainActivity main;
    private final BluetoothAdapter BluetoothAdapter;
    private final BluetoothLeScanner bluetoothLeScanner;
    private final Handler handler;
    private final long scanPeriod;
    private final int signalStrength;
    private boolean scanning;

    public Scanner_BLE(MainActivity mainActivity, long scanPeriod, int signalStrength) {
        main = mainActivity;

        handler = new Handler();

        this.scanPeriod = scanPeriod;
        this.signalStrength = signalStrength;

        final BluetoothManager bluetoothManager = (BluetoothManager) main.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter = bluetoothManager.getAdapter();

        bluetoothLeScanner = BluetoothAdapter.getBluetoothLeScanner();

    }

    public boolean isScanning() {
        return scanning;
    }

    public void start() {
        if (BluetoothAdapter == null || !BluetoothAdapter.isEnabled()) {
            Utils.toast(main.getApplicationContext(), "Bluetooth is not enabled!");
            main.stopScan();
        }
        else {
            scanBLeDevice(true);
        }
    }

    public void stop() {
        scanBLeDevice(false);
    }

    private void scanBLeDevice(final boolean enable) {
        if (enable && !scanning) {

            handler.postDelayed(() -> {
                scanning = false;
                bluetoothLeScanner.stopScan(scanCallback);
                main.stopScan();
            }, scanPeriod);

            scanning = true;

            bluetoothLeScanner.startScan(scanCallback);
        }
        else {
            scanning = false;
            try {
                bluetoothLeScanner.stopScan(scanCallback);
            }
            catch (Exception ignored) {}
        }
    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            handleScanResult(result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            for (ScanResult result : results) {
                handleScanResult(result);
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Utils.toast(main.getApplicationContext(), "Scan failed with error: " + errorCode);
        }
    };

    private void handleScanResult(ScanResult result) {
        BluetoothDevice device = result.getDevice();
        int rssi = result.getRssi();
        if (rssi > signalStrength) {
            handler.post(() -> main.addDevice(device, rssi));
        }
    }
}
