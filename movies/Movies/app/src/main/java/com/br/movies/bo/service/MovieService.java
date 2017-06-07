package com.br.movies.bo.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.br.movies.MoviesApplication;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.domain.MovieSearch;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by danilorangel on 04/06/17.
 */

public class MovieService {

    private static MovieService instance;

    private MovieService() {

    }

    public static MovieService getInstance() {
        if (instance == null) {
            instance = new MovieService();
        }

        return instance;
    }


    public void search(Context context, String name, final OnSearch onSearch) {
        try {
            if (name == null || name.trim().equals("")) {
                return;
            }
            HashMap<String, Object> param = new HashMap<>();
            param.put("name", name);
            MoviesApplication.getApplication().getServiceUtil().callService(ServiceUrl.SEARCH_BY_NAME, Request.Method.GET, param, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    try {
                        JSONArray array = result.getJSONArray("movies");
                        List<MovieSearch> movies = new ArrayList<MovieSearch>(Arrays.asList(new Gson().fromJson(array.toString(), MovieSearch[].class)));
                        onSearch.onSearchFinish(movies);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String service, VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchByGenre(Context context, String id, final OnSearch onSearch) {
        try {
            if (id == null || id.trim().equals("")) {
                return;
            }
            HashMap<String, Object> param = new HashMap<>();
            param.put("id", id);
            MoviesApplication.getApplication().getServiceUtil().callService(ServiceUrl.GET_GENRES_ID, Request.Method.GET, param, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    try {
                        JSONArray array = result.getJSONArray("movies");
                        List<MovieSearch> movies = new ArrayList<MovieSearch>(Arrays.asList(new Gson().fromJson(array.toString(), MovieSearch[].class)));
                        onSearch.onSearchFinish(movies);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String service, VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnSearch {
        public void onSearchFinish(List<MovieSearch> movies);
        public void onError();
    }


}
