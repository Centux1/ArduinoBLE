package com.example.arduinoble;

import android.content.Context;

import android.view.Gravity;
import android.widget.Toast;

public class Utils {
    public static void toast(Context context, String string) {
        Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        toast.show();
    }
}
