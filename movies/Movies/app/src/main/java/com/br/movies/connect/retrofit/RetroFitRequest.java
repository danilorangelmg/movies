package com.br.movies.connect.retrofit;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Danilo.
 */
public interface RetroFitRequest {

//    @Headers() colocar o Headers se precisar

//    @FormUrlEncoded
    @GET("/?plot=full")
    Call<Map> getMoviesList(@Query("s") String title);




}
