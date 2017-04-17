package com.br.movies.bo.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.br.movies.bo.contract.GenericResponse;
import com.br.movies.bo.contract.OfferResponse;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.connect.volley.ServiceUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danilorangel on 16/04/17.
 */

public class OfferService {

    private static OfferService instance;

    private OfferService() {

    }

    public static OfferService getInstance() {
        if (instance == null) {
            instance = new OfferService();
        }

        return instance;
    }

    public void getMovieValue(Context context, final OfferResponse onResponse) {
        try {
            new ServiceUtil(context).callService(ServiceUrl.GET_MOVIE_VALUE, Request.Method.GET, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    onResponse.onSuccess(result);
                }

                @Override
                public void onError(String service, VolleyError error) {
                    onResponse.onError(error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendNewOffer(Context context, int qtd, String value, final GenericResponse genericResponse) {

        if (qtd == 0 && (value == null || value.trim().equals(""))) {
            return;
        }

        try {
            JSONObject payload = new JSONObject();
            payload.put("price", value);
            payload.put("movieCount", qtd);

            new ServiceUtil(context).callService(ServiceUrl.NEW_OFFER, Request.Method.POST, payload.toString(), new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    genericResponse.onSuccess();
                }

                @Override
                public void onError(String service, VolleyError error) {
                    genericResponse.onError(error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
