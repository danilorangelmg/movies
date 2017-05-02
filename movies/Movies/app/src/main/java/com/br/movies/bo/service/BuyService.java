package com.br.movies.bo.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.br.movies.bo.util.SharedPersistence;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.connect.volley.ServiceUtil;

import org.json.JSONObject;

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

    public interface OnBuy {
        public void onSucess();
        public void onError(VolleyError error);
    }

}
