package com.crashreportutils.android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.text.format.Time;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sacredoon on 8/20/15.
 */
public class CrashUtils {

    public static final String[] USER_DATA = {"Nava", "Yelena", "Liat", "Limor", "Yoni", "Ofer"};
    public static final String DOMAIN = "@shellanoo.com";

    public static ArrayList<String> getUserList(Context context) {

        String priorityName = null;
        ArrayList<String> arrayList = new ArrayList();
        for (int i = 0; i < USER_DATA.length; i++) {
            String name = USER_DATA[i];
            String priorityLastName = Pref.getPriorityLastName(context);
            if (priorityLastName == null || (priorityLastName != null && !priorityLastName.equalsIgnoreCase(name))) {
                //User user = new User(name);
                arrayList.add(name);
            } else {
                priorityName = name;
            }
        }
        String addedUserName = Pref.getNewUser(context);
        if (addedUserName != null) {
            if (priorityName == null) {
                priorityName = addedUserName;
            } else {
                arrayList.add(addedUserName);
            }
        }
        if (priorityName != null) {
            // User user = new User(priorityName);
            arrayList.add(0, priorityName);
        }
        return arrayList;

    }

    public static String getDeviceInfo(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
        }
        String model = Build.MODEL;
        if (!model.startsWith(Build.MANUFACTURER)) {
            model = Build.MANUFACTURER + " " + model;
        }

        String deviceInfo = ("Android version: " + Build.VERSION.SDK_INT + "\n" +
                "Device: " + model + "\n" +
                "App version: " + (info == null ? "(null)" : info.versionCode) + "\n" +
                "Time: " + getCurrentTime());
        return deviceInfo;
    }

    public static String getCurrentTime() {
        Time time = new Time();
        time.setToNow();
        String nowInString = time.monthDay + "/" + time.month + "/" + time.year + " " + time.hour + ":" + time.minute;
        return nowInString;
    }


    public static void sendLogFile(String error, String mail, Context context) {

        String deviceInfo = getDeviceInfo(context);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Crash log file");
        intent.putExtra(Intent.EXTRA_TEXT, deviceInfo + "\n ---------------------- " + "\n" + error);
        context.startActivity(intent);

    }

    public static void crashApplication(int delay) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView textView = null;
                textView.setText("crasjed!");
            }
        }, delay);
    }


}
