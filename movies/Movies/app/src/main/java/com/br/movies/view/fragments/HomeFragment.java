package com.br.movies.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.br.movies.MoviesApplication;
import com.br.movies.R;
import com.br.movies.bo.adapter.CategoryAdapter;
import com.br.movies.bo.adapter.HomeBannerAdapter;
import com.br.movies.bo.adapter.HomeCategoryAdapter;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.domain.Banner;
import com.br.movies.domain.HomeCategory;
import com.br.movies.domain.MovieSearch;
import com.br.movies.bo.util.Util;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 08/01/17.
 */

public class HomeFragment extends Fragment {

    @Bind(R.id.bannerNews)
    ViewPager bannerNews;

    @Bind(R.id.recyclerViewHome)
    RecyclerView recyclerView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }


    private void init() {
        load();
        loadBanners();
        List<String> values = new ArrayList<>();
    }

    private void load() {
        try {

            if (Util.getHomeCategorys() != null) {
                loadMovies(Util.getHomeCategorys());
                return;
            }

            MoviesApplication.getApplication().getServiceUtil().callService(ServiceUrl.GET_HOME_MOVIES, Request.Method.GET, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    try {
                        HomeCategory[] homeCategoryArray = new Gson().fromJson(result.getJSONArray("categories").toString(), HomeCategory[].class);
                        List<HomeCategory> listHomeCategories = new ArrayList<HomeCategory>(Arrays.asList(homeCategoryArray));
                        Util.setHomeCategorys(listHomeCategories);
                        loadMovies(listHomeCategories);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String service, JSONObject error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMovies(List<HomeCategory> listHomeCategories) {
        HomeCategoryAdapter adapter = new HomeCategoryAdapter(listHomeCategories, getActivity());
        adapter.setOnMovieClick(new CategoryAdapter.OnMovieClick() {
            @Override
            public void onMovieClick(MovieSearch movieSearch) {
                Util.loadDetail(getActivity(), movieSearch.getId(), isVisible());
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadBanners() {
        try {
            MoviesApplication.getApplication().getServiceUtil().callService(ServiceUrl.GET_BANNERS, Request.Method.GET, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    try {
                        JSONArray banners = result.getJSONArray("banners");
                        Banner[] arrayBanner = new Gson().fromJson(banners.toString(), Banner[].class);
                        List<Banner> listbanners = new ArrayList<Banner>(Arrays.asList(arrayBanner));
                        HomeBannerAdapter bannerAdapter = new HomeBannerAdapter(listbanners);
                        bannerNews.setAdapter(bannerAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String service, JSONObject error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
