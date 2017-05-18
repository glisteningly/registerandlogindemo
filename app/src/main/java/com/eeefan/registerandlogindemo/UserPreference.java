package com.eeefan.registerandlogindemo;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class UserPreference {
    private static SharedPreferences mUserPreferences;
    private static final String USER_PREFERENCE = "user_preference";
    
    public static SharedPreferences ensureIntializePreference() {
        if (mUserPreferences == null) {
            mUserPreferences = ItLanbaoLibApplication.getInstance().getSharedPreferences(USER_PREFERENCE, 0);
        }
        return mUserPreferences;
    }

    public static void save(String key, String value) {
        Editor editor = ensureIntializePreference().edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static void save(String key, boolean value) {
        Editor editor = ensureIntializePreference().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String read(String key, String defaultvalue) {
        return ensureIntializePreference().getString(key, defaultvalue);
    }

    public static boolean read(String key, boolean defaultvalue) {
        return ensureIntializePreference().getBoolean(key, defaultvalue);
    }

    public static void del(String key){
        Editor editor = ensureIntializePreference().edit();
        editor.remove(key);
        editor.apply();
    }
    
}
