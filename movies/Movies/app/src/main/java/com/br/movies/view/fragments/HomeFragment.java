package com.br.movies.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.movies.MoviesApplication;
import com.br.movies.R;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by danilorangel on 08/01/17.
 */

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Map<String, Object> params = new HashMap<>();
        params.put("title", "batman");

//        MoviesApplication.getApplication().getServiceRetrofit().callService(ServiceUrl.GET_MOVIES, new ResultService() {
//            @Override
//            public void onSucess(String service, JSONObject result) {
//
//            }
//
//            @Override
//            public void onError(String service, JSONObject error) {
//
//            }
//        }, params);


        return view;
    }
}
