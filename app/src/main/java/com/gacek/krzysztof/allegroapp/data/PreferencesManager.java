package com.gacek.krzysztof.allegroapp.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;



public class PreferencesManager {

    private static final String
            PREFERENCES_NAME = "com.gacek.krzysztof.allegroapp.app_pref";
    public static final String USER_LOGIN_KEY = "userLogin";
    public static final String USER_PASSWORD_KEY = "userPassword";
    public static final String COUNTRY_CODE_KEY = "countryCode";
    public static final String LOCAL_VERSION_KEY = "webApiKey";
    public static final String WEB_API_KEY = "webApiCode";
    public static final String CATEGORY_VER_KEY = "catVerKey";
    public static final String CATEGORY_VER_STR = "catVerStr";
    public static final String SESSION_ID_KEY = "sessionId";
    public static final String CITY_KEY = "city";
    public static final String POST_CODE_KEY = "postCode";
    public static final String STATE_KEY = "state";


    private static PreferencesManager sPreferencesManager;
    private final SharedPreferences sharedPreferences;

    private PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized  void initializeInstance(Context context) {
        if (sPreferencesManager == null) {
            sPreferencesManager = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if(sPreferencesManager == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() + " is not initialized");
        }
        return sPreferencesManager;
    }

    public void putStringValue(String key, String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    public String getStringValue(String key) {
        return sharedPreferences.getString(key, null);
    }

    public int getIntValue(String key) {
        return Integer.parseInt(sharedPreferences.getString(key, "-1"));
    }

    public void remove(String key) {
        sharedPreferences.edit()
                .remove(key)
                .apply();
    }

    public boolean clear() {
        return sharedPreferences.edit()
                .clear()
                .commit();
    }

}
