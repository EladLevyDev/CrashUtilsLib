package com.crashreportutils.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

public class Pref {


    // Constants
    private static final String USER_NAME = "user_name";
    private static final String PRIORITY_LAST_NAME = "last_name";
    // general
    private static SharedPreferences pref;

    private static SharedPreferences getPrefs(Context context) {
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        }
        return pref;
    }

    public static void setPriority(Context context, String name, int priority) {
        String oldPriority = getPriorityLastName(context);
        if(oldPriority != null && !oldPriority.equalsIgnoreCase(name) ){
            setPriority(context, oldPriority, 0);
        }
        setInt(context, name, priority);
        setPriorityLastName(context, name);
    }

    public static Integer getPriority(Context context, String name) {
        return getInt(context, name, 0);
    }

    public static void setPriorityLastName(Context context, String name) {
        setString(context, PRIORITY_LAST_NAME, name);
    }
    public static String getPriorityLastName(Context context) {
        return getString(context, PRIORITY_LAST_NAME, null);
    }

    // proxy methods
    public static String getString(Context context, String key, String defValue) {
        return getPrefs(context).getString(key, defValue);
    }

    public static Boolean getBoolean(Context context, String key, boolean defValue) {
        try {
            return getPrefs(context).getBoolean(key, defValue);
        } catch (ClassCastException e) {
            String s = getPrefs(context).getString(key, String.valueOf(defValue));
            return "1".equals(s) || "yes".equalsIgnoreCase(s) || "true".equalsIgnoreCase(s) || Boolean.valueOf(s);
        }
    }

    public static Float getFloat(Context context, String key, float defValue) {
        try {
            return getPrefs(context).getFloat(key, defValue);
        } catch (ClassCastException e) {
            return Float.valueOf(getPrefs(context).getString(key, String.valueOf(defValue)));
        }
    }

    public static Integer getInt(Context context, String key, int defValue) {
        try {
            return getPrefs(context).getInt(key, defValue);
        } catch (ClassCastException e) {
            return Integer.valueOf(getPrefs(context).getString(key, String.valueOf(defValue)));
        }
    }

    public static Long getLong(Context context, String key, long defValue) {
        try {
            return getPrefs(context).getLong(key, defValue);
        } catch (ClassCastException e) {
            return Long.valueOf(getPrefs(context).getString(key, String.valueOf(defValue)));
        }
    }


    public static Set<String> getStringSet(Context context, String key, Set<String> defValues) {
        return getPrefs(context).getStringSet(key, defValues);
    }

    public static void setString(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).apply();
    }

    public static void setBoolean(Context context, String key, Boolean value) {
        getPrefs(context).edit().putBoolean(key, value).apply();
    }

    public static SharedPreferences.Editor setFloat(Context context, String key, Float value) {
        return getPrefs(context).edit().putFloat(key, value);
    }

    public static void setInt(Context context, String key, Integer value) {
        getPrefs(context).edit().putInt(key, value).commit();
    }

    public static void setLong(Context context, String key, Long value) {
        getPrefs(context).edit().putLong(key, value).apply();
    }

    public static SharedPreferences.Editor putBoolean(Context context, String key, boolean value) {
        return getPrefs(context).edit().putBoolean(key, value);
    }


    public static void addNewUser(Context context, String value) {
        if (value != null) {
            setString(context, USER_NAME, value);
            setPriority(context, value, 1);
        }
    }

    public static String getNewUser(Context context) {
        return getString(context, USER_NAME, null);
    }
}
