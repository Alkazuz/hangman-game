package ufms.hangman.game.utils;

import android.content.SharedPreferences;

public class LocalStorageManager {
    private static final String PREF_NAME = "ufms.hangman.game.PREF_NAME";

    private static LocalStorageManager instance;
    private SharedPreferences sharedPreferences;

    public static LocalStorageManager getInstance() {
        if (instance == null) {
            instance = new LocalStorageManager();
        }
        return instance;
    }

    public void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String get(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
