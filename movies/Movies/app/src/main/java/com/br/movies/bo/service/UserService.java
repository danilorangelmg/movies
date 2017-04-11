package com.br.movies.bo.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.br.movies.bo.contract.GenericResponse;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.connect.volley.ServiceUtil;

import org.json.JSONObject;

/**
 * Created by danilorangel on 11/04/17.
 */

public class UserService {

    private static UserService instance;

    private UserService() {

    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    public void doLogin(Context context, String user, String password, final GenericResponse onResponse) {

        if (user == null || password == null || user.trim().equals("") || password.trim().equals("")) {
            return;
        }

        try {
            JSONObject param = new JSONObject();
            param.put("username", user);
            param.put("password", password);
            new ServiceUtil(context).callService(ServiceUrl.DO_LOGIN, Request.Method.POST, param.toString(), new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    onResponse.onSucess();
                }

                @Override
                public void onError(String service, JSONObject error) {
                    onResponse.onError(new VolleyError(error.toString()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doLogout() {

    }

    public void getUser() {

    }


}
