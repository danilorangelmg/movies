package com.br.movies.bo.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.br.movies.MoviesApplication;

/**
 * Created by danilorangel on 22/04/17.
 */

public class SharedPersistence {

    private static String USER_ID;

    private static SharedPersistence instance;
    private SharedPreferences sharedPreferences;

    private SharedPersistence() {
        config();
    }

    public static SharedPersistence getInstance() {
        if (instance == null) {
            instance = new SharedPersistence();
        }

        return instance;
    }

    private void config() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MoviesApplication.getApplication());
    }

    public void saveUserId(String userId) {
        saveString(USER_ID, userId);
    }

    public String getUserId() {
      return sharedPreferences.getString(USER_ID, "");
    }

    private void saveString(String key, String value) {
              sharedPreferences.edit()
                .putString(key, value)
                .commit();
    }

    private void removeString(String key) {
        if (sharedPreferences.contains(key)) {
            sharedPreferences
                    .edit()
                    .remove(key)
                    .commit();
        }
    }

    public void clearAll() {
        removeString(USER_ID);
    }


}
