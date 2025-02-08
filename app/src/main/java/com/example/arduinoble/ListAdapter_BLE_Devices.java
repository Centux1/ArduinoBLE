package com.example.arduinoble;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ListAdapter_BLE_Devices extends ArrayAdapter<BLE_Device> {

    private final Activity activity;
    private final int layoutResourceID;
    private final ArrayList<BLE_Device> devices;

    public ListAdapter_BLE_Devices(Activity activity, int resource, ArrayList<BLE_Device> objects) {
        super(activity.getApplicationContext(), resource, objects);

        this.activity = activity;
        layoutResourceID = resource;
        devices = objects;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceID, parent, false);
        }

        BLE_Device device = devices.get(position);
        String name = device.getName();
        String address = device.getAddress();
        int rssi = device.getRSSI();

        TextView tv;

        tv = convertView.findViewById(R.id.tv_name);
        if (name != null && !name.isEmpty()) {
            tv.setText(name);
        }
        else {
            tv.setText("No Name");
        }

        tv = convertView.findViewById(R.id.tv_rssi);
        tv.setText("RSSI: " + rssi);

        tv = convertView.findViewById(R.id.tv_macaddr);
        if (address != null && !address.isEmpty()) {
            tv.setText(address);
        }
        else {
            tv.setText("No Address");
        }

        return convertView;
    }
}