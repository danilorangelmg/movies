package com.br.movies.bo.contract;

import com.android.volley.VolleyError;

/**
 * Created by danilorangel on 11/04/17.
 */

public interface GenericResponse {

    public void onSuccess();
    public void onError(VolleyError volleyError);

}
