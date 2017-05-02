package com.br.movies.bo.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.br.movies.bo.contract.GenericResponse;
import com.br.movies.bo.util.SharedPersistence;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.connect.volley.ServiceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.http.POST;

/**
 * Created by danilorangel on 22/04/17.
 */

public class RentService {

    private static RentService instance;

    private RentService() {

    }

    public static RentService getInstance() {
        if (instance == null) {
            instance = new RentService();
        }

        return instance;
    }


    public void doRent(Context context, String movieId, final GenericResponse response) {

        try {
            String userId = SharedPersistence.getInstance().getUserId();

            JSONObject payload = new JSONObject();
            payload.put("movieId", movieId);
            payload.put("userId", userId);

            new ServiceUtil(context).callService(ServiceUrl.RENT, Request.Method.POST, payload.toString(), new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    response.onSuccess();
                }

                @Override
                public void onError(String service, VolleyError error) {
                    response.onError(error);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
