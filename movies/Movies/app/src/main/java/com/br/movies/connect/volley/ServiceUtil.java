package com.br.movies.connect.volley;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.br.movies.MoviesApplication;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.volley.VolleyRequest;
import com.br.movies.domain.Const;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.util.Map;

/**
 * Created by zupmacpro on 10/24/16.
 */

public class ServiceUtil {

    private String url;
    private Context context;
    private boolean generate = false;

    public ServiceUtil(Context context) {
        this.context = context;
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
    }

    public void callService(String service, int method, ResultService resultService) throws Exception {
        callService(service, method, null, null, resultService, Const.DEFAULT_VOLLEY_TAG);
    }

    public void callService(String service, int method, Map<String, Object> params, ResultService resultService) throws Exception {
        callService(service, method, null, params, resultService, Const.DEFAULT_VOLLEY_TAG);
    }

    public void callService(String service, int method, String body, ResultService resultService) throws Exception {
        callService(service, method, body, null, resultService, Const.DEFAULT_VOLLEY_TAG);
    }

    public void callService(String service, int method, String body, ResultService resultService, String tag) throws Exception {
        callService(service, method, body, null, resultService, tag);
    }

    public void callService(final String service, final int method, final String body, final Map<String, Object> params, final ResultService resultService, final String tag) throws Exception {
        url = Const.BASE_URL;
        VolleyService volleyService = new VolleyService();

        String urlService = String.valueOf(service);
        if (params != null && params.size() > 0) {
            urlService = mountParamsUrl(service, params);
        }

        url = url.concat(urlService);

        Log.i("SERVICE-VOLLEY", "CALL SERVICE - "+url);

        if (tag != null) {
            volleyService.call(urlService, method, body, resultService, tag);
        } else {
            volleyService.call(urlService, method, body, resultService);
        }
    }


    private String mountParamsUrl(String service, Map<String, Object> params) throws RuntimeException {

        String urlService = service;

        for(Map.Entry<String, Object> param : params.entrySet()) {
            String paramMustache = String.valueOf("{{").concat(param.getKey()).concat("}}");
            urlService = urlService.replace(paramMustache, param.getValue().toString());
        }

        if (urlService.contains("{") || urlService.contains("}")) {
            new RuntimeException("Do not expect the values {}. Please check the parameters informed");
        }

        return urlService;
    }

    public void cancelPendingTransaction(String tag) {
//        AppController.getInstance().cancelPendingVolleyRequests(tag);
    }

    public void cancelPendingTransaction() {
//        AppController.getInstance().cancelPendingVolleyRequests(SessionUtil.DEFAULT_VOLLEY_TAG);
    }


    private class VolleyService {

        public VolleyService() {

        }

        private int method;

        private void call(String service, int method, String body, ResultService result) throws Exception {
            this.method = method;
            final VolleyRequest VolleyRequest = new VolleyRequest(context, method, ServiceUtil.this.url, body, onSucess(service, result), onError(service, body, null, result), generate);
            MoviesApplication.getApplication().addToRequestQueue(VolleyRequest);
        }

        private void call(String service, int method, String body, ResultService result, String tag) throws Exception {
            this.method = method;
            final VolleyRequest VolleyRequest = new VolleyRequest(context, method, ServiceUtil.this.url, body, onSucess(service, result), onError(service, body, tag, result), generate);
            MoviesApplication.getApplication().addToRequestQueue(VolleyRequest);
        }


        private Response.Listener<JSONObject> onSucess(final String service, final ResultService resultService) {
            Response.Listener<JSONObject> sucess = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("SERVICE-VOLLEY", "SUCESS SERVICE - "+service);
                    resultService.onSucess(service, response);
                }
            };

            return sucess;
        }

        private Response.ErrorListener onError(final String service, final String body, final String tag, final ResultService resultService) {
            Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null) {
                        String result = new String(networkResponse.data);
                        JSONObject responseError = new Gson().fromJson(result, JSONObject.class);
                        resultService.onError(service, responseError);
                    }

                    Log.i("SERVICE-VOLLEY", "ERRROR SERVICE - "+service);
                }
            };

            return error;
        }

    }

}
