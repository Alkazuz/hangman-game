package ufms.hangman.game.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class LocalStorageManager {
    private static final String PREF_NAME = "ufms.hangman.game.PREF_NAME";

    private static LocalStorageManager instance;
    private SharedPreferences sharedPreferences;

    private LocalStorageManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        instance = new LocalStorageManager(context);
    }
    public static LocalStorageManager getInstance() {
        return instance;
    }

    public void save(String key, Object value) {
        String json = new Gson().toJson(value);
        save(key, json);
    }

    public void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String get(String key) {
        return sharedPreferences.getString(key, null);
    }

    public Object loadObject(String key) {
        String json = get(key);
        if (json == null) {
            return null;
        }
        return new Gson().fromJson(json, Object.class);
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
