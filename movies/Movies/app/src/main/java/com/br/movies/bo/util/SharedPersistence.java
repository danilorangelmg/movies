package com.br.movies.bo.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.br.movies.MoviesApplication;
import com.br.movies.domain.Const;
import com.br.movies.domain.Rent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by danilorangel on 22/04/17.
 */

public class SharedPersistence {


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
        saveString(Const.USER_ID, userId);
    }

    public String getUserId() {
      return sharedPreferences.getString(Const.USER_ID, "");
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
        removeString(Const.USER_ID);
        removeString(Const.USER_RENTS);
    }

    public void saveUsersRents(List<Rent> rents) {
        String json = new Gson().toJson(rents);
        saveString(Const.USER_RENTS, json);
    }

    public List<Rent> getUserRents() {
        String json = sharedPreferences.getString(Const.USER_RENTS, "");
        if (json.trim().equals("")) {
            return new ArrayList<Rent>();
        }
        return new ArrayList<>(Arrays.asList(new Gson().fromJson(json, Rent[].class)));
    }

}
