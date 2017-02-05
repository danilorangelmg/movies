package com.br.movies.connect;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by danilorangel on 22/01/17.
 */

public interface ResultService {

    public void onSucess(String service, JSONObject result);
    public void onError(String service, JSONObject error);

}
