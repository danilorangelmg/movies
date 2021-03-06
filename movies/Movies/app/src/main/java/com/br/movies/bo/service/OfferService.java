package com.br.movies.bo.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.br.movies.bo.contract.GenericResponse;
import com.br.movies.bo.contract.OfferResponse;
import com.br.movies.bo.util.SharedPersistence;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.connect.volley.ServiceUtil;
import com.br.movies.domain.Offer;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void sendNewOffer(Context context, int qtd, String value, final OnNewOfferResponse onNewOfferResponse) {

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
                    try {
                        String offerId = result.getString("offerId");
                        onNewOfferResponse.onSuccess(offerId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String service, VolleyError error) {
                    onNewOfferResponse.onError(error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUserOffer(Context context, final OnOfferResponse onOfferResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", SharedPersistence.getInstance().getUserId());
        try {
            new ServiceUtil(context).callService(ServiceUrl.GET_OFFER_BY_USER, Request.Method.GET, params, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    Offer offer = new Gson().fromJson(result.toString(), Offer.class);
                    onOfferResponse.onSuccess(offer);
                }

                @Override
                public void onError(String service, VolleyError error) {
                    onOfferResponse.onError(error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeOffer(Context context, String offerId, final GenericResponse onResponse) {
        try {
            JSONObject payload = new JSONObject();
            payload.put("offerId", offerId);
            payload.put("userId", SharedPersistence.getInstance().getUserId());
            new ServiceUtil(context).callService(ServiceUrl.CHANGE_OFFER, Request.Method.POST, payload.toString(), new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    onResponse.onSuccess();
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

    public void getAdditionalOffers(Context context, final OnAdditionalOfferResponse onResponse) {
        try {
            new ServiceUtil(context).callService(ServiceUrl.GET_ADDITIONAL_OFFERS, Request.Method.GET, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    try {
                        JSONArray array = result.getJSONArray("array");
                        Offer[] offers = new Gson().fromJson(array.toString(), Offer[].class);
                        List<Offer> lOffers = new ArrayList<Offer>(Arrays.asList(offers));
                        onResponse.onSuccess(lOffers);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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


    public interface OnNewOfferResponse {
        public void onSuccess(String offerId);
        public void onError(VolleyError error);
    }

    public interface OnOfferResponse {
        public void onSuccess(Offer offer);
        public void onError(VolleyError error);
    }

    public interface OnAdditionalOfferResponse {
        public void onSuccess(List<Offer> offers);
        public void onError(VolleyError error);
    }


}
