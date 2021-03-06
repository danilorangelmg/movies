package com.br.movies.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.br.movies.MoviesApplication;
import com.br.movies.bo.service.RentService;
import com.br.movies.bo.util.SharedPersistence;
import com.br.movies.bo.util.Util;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.domain.HomeCategory;
import com.br.movies.domain.Rent;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by danilorangel on 02/04/17.
 */

public class HomeService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("SERVICE-HOME","Start");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            Log.i("HOME-SERVICE", "START-command");
            MoviesApplication.getApplication().getServiceUtil().callService(ServiceUrl.GET_HOME_MOVIES, Request.Method.GET, new ResultService() {

                @Override
                public void onSucess(String service, JSONObject result) {
                    try {
                        HomeCategory[] homeCategoryArray = new Gson().fromJson(result.getJSONArray("categories").toString(), HomeCategory[].class);
                        List<HomeCategory> listHomeCategories = new ArrayList<HomeCategory>(Arrays.asList(homeCategoryArray));
                        Util.setHomeCategorys(listHomeCategories);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String service, VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        getUserRents();

        return super.onStartCommand(intent, flags, startId);
    }

    private void getUserRents() {
        String userId = SharedPersistence.getInstance().getUserId();
        RentService.getInstance().getUserRent(getApplicationContext(), userId, new RentService.OnTakeRent() {
            @Override
            public void onSucess(List<Rent> rentList) {
                if (rentList.size() > 0) {
                    SharedPersistence.getInstance().saveUsersRents(rentList);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}
