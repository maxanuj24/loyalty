package com.example.stpl.loyality_ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by stpl on 26/3/18.
 */

public class LoyalitySharedPref {

    private static SharedPreferences pref;

    private static Context context;

    private static final String SHARED_PREF_NAME = "loyalty_shared_pref";

    public static final String USERNAME = "username";

//    public LoyalitySharedPref(Context context) {
//        pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//    }

    public static void setString(String key, String value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        LoyalitySharedPref.context = context;
        return pref.getString(key, "");
    }
}
