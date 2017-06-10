package com.br.movies.bo.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.br.movies.bo.util.SharedPersistence;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.connect.volley.ServiceUtil;
import com.br.movies.domain.Buy;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by danilorangel on 22/04/17.
 */

public class BuyService {

    private static BuyService instance;

    private BuyService() {

    }

    public static BuyService getInstance() {
        if (instance == null) {
            instance = new BuyService();
        }
        return instance;
    }

    public void doBuy(Context context, String offerId, final OnBuy onBuy) {
        try {
            String userId = SharedPersistence.getInstance().getUserId();
            JSONObject payload = new JSONObject();
            payload.put("userId", userId);
            payload.put("offerId", offerId);
            new ServiceUtil(context).callService(ServiceUrl.BUY, Request.Method.POST, payload.toString(), new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    onBuy.onSucess();
                }

                @Override
                public void onError(String service, VolleyError error) {
                    onBuy.onError(error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUserBuy(Context context, String userId, final OnTakeBuy onTakeBuy) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            new ServiceUtil(context).callService(ServiceUrl.GET_BUY, Request.Method.GET, params, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    try {
                        JSONArray array = result.getJSONArray("array");
                        Buy[] buy = new Gson().fromJson(array.toString(), Buy[].class);
                        List<Buy> buysList = new ArrayList<Buy>(Arrays.asList(buy));
                        onTakeBuy.onSucess(buysList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String service, VolleyError error) {
                    onTakeBuy.onError(error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnBuy {
        public void onSucess();
        public void onError(VolleyError error);
    }

    public interface OnTakeBuy {
        public void onSucess(List<Buy> buyList);
        public void onError(VolleyError error);
    }

}
