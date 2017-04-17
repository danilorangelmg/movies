package com.br.movies.bo.contract;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by danilorangel on 16/04/17.
 */

public interface OfferResponse {

    public void onSuccess(JSONObject response);
    public void onError(VolleyError volleyError);

}
