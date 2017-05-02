package com.br.movies.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.br.movies.MoviesApplication;
import com.br.movies.R;
import com.br.movies.bo.adapter.CategoryAdapter;
import com.br.movies.bo.contract.GenericResponse;
import com.br.movies.bo.service.RentService;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.domain.Movie;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.br.movies.domain.MovieSearch;
import com.br.movies.bo.util.Util;
import com.br.movies.view.activity.MainActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danilorangel on 01/04/17.
 */

public class MovieDetailFragment extends Fragment  implements AppBarLayout.OnOffsetChangedListener, MainActivity.OnBackPressed {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;
    private static final String MOVIE_ATTR = "movie";

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    private Movie movie;
    private String strMovie;

    @Bind(R.id.main_linearlayout_title)
    LinearLayout mTitleContainer;

    @Bind(R.id.main_textview_title)
    TextView mTitle;

    @Bind(R.id.main_appbar)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.main_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.imgPoster)
    NetworkImageView imgPoster;

    @Bind(R.id.imgLogo)
    NetworkImageView imgLogo;

    @Bind(R.id.txtBigTitle)
    TextView txtTitleBigText;

    @Bind(R.id.overview)
    TextView txtOverview;

    @Bind(R.id.recyclerviewSimilarMovies)
    RecyclerView recyclerViewSimilarMovies;

    @Bind(R.id.ratingContainer)
    LinearLayout ratingContainer;

    @Bind(R.id.rating)
    TextView txtRating;

    @Bind(R.id.btnRent)
    CardView btnRent;

    @Bind(R.id.txtRent)
    TextView txtRent;

    public static MovieDetailFragment newInstance(String movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_ATTR, movie);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strMovie = getArguments().getString(MOVIE_ATTR);
        ((MainActivity)getActivity()).setOnBackPressed(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        ButterKnife.bind(this, view);

        movie = new Gson().fromJson(strMovie, Movie.class);


        init();

//        mToolbar.inflateMenu(R.menu.menu_main);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setOnBackPressed(this);
    }

    private void init() {
        loadSimilarMovies(String.valueOf(movie.getId()));
        imgPoster.setImageUrl(movie.getBackdrop_path(), MoviesApplication.getApplication().getImageLoader());
        imgLogo.setImageUrl(movie.getPoster_path(), MoviesApplication.getApplication().getImageLoader());
        mTitle.setText(movie.getName());
        txtTitleBigText.setText(movie.getName());
        txtOverview.setText(movie.getOverview());
        txtRating.setText(movie.getPopularity());
        mAppBarLayout.addOnOffsetChangedListener(this);
        loadRating(0d);
    }

    @OnClick(R.id.btnRent)
    public void rentMovie() {
        RentService.getInstance().doRent(getActivity(), String.valueOf(movie.getId()), new GenericResponse() {
            @Override
            public void onSuccess() {
                btnRent.setEnabled(false);
                txtRent.setText(R.string.rented);
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }

    private void loadSimilarMovies(String id) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("movie_id",String.valueOf(id));
            MoviesApplication.getApplication().getServiceUtil().callService(ServiceUrl.GET_SIMILAR_MOVIES, Request.Method.GET, params, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    try {
                        JSONArray movies = result.getJSONArray("movies");
                        MovieSearch[] movieSearches = new Gson().fromJson(movies.toString(), MovieSearch[].class);
                        List<MovieSearch> listSimilarMovies = new ArrayList<MovieSearch>(Arrays.asList(movieSearches));
                        CategoryAdapter adapter = new CategoryAdapter(listSimilarMovies);
                        adapter.setMovieClick(similarMovieOnClick());
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerViewSimilarMovies.setHasFixedSize(true);
                        recyclerViewSimilarMovies.setLayoutManager(mLayoutManager);
                        recyclerViewSimilarMovies.setAdapter(adapter);
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

    private CategoryAdapter.OnMovieClick similarMovieOnClick() {
        CategoryAdapter.OnMovieClick onMovieClick = new CategoryAdapter.OnMovieClick() {

            @Override
            public void onMovieClick(MovieSearch movieSearch) {
                Util.loadDetail(getActivity(), movieSearch.getId(), isVisible());
            }
        };

        return onMovieClick;
    }

    private void loadRating(Double rating) {
        for(int i = 0; i<5; i++) {
            ImageView view = new ImageView(getActivity());
            view.setPadding(0, 10, 0, 0);
            view.setImageResource(R.drawable.star_full);
//            ratingContainer.addView(view);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        if (v == null) {
            return;
        }
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onBack() {
        getActivity().getSupportFragmentManager().popBackStack(String.valueOf(movie.getId()), 0);
    }
}
