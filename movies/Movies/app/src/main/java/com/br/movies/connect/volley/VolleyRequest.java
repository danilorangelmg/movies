package com.br.movies.connect.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Danilo on 18/09/15.
 */
public class VolleyRequest extends JsonRequest<JSONObject> {


    public VolleyRequest(final Context context, int method, String url, String requestBody, final Response.Listener<JSONObject> listener, final Response.ErrorListener errorListener, boolean generate) {
        super(method, url, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorListener != null) {
                    errorListener.onErrorResponse(error);
                }
            }
        });
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsReturn = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));

            boolean isArray = jsReturn.startsWith("[");

            JSONObject returnJsn = null;
            if (isArray) {
                JSONArray jsonArray = new JSONArray(jsReturn);
                returnJsn = new JSONObject();
                returnJsn.put("array", jsonArray);
            } else {
                returnJsn = new JSONObject(jsReturn);
            }
            returnJsn.put("headers", new JSONObject(response.headers));
            return Response.success(returnJsn, HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        //inserir os HEADERS SE PRECISAR
        return super.getHeaders();
    }

}
