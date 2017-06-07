package com.br.movies.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.movies.R;
import com.br.movies.bo.adapter.SearchAdapter;
import com.br.movies.bo.service.MovieService;
import com.br.movies.bo.util.Util;
import com.br.movies.domain.MovieSearch;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 04/06/17.
 */

public class SearchFragment extends Fragment {

    private static final String QUERY = "query";
    private static final String GENRES = "genres";
    private String query;
    private boolean genres;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    public static SearchFragment newInstance(String query, boolean genres) {
        SearchFragment f = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(QUERY, query);
        bundle.putBoolean(GENRES, genres);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        query = getArguments().getString(QUERY);
        genres = getArguments().getBoolean(GENRES);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_movie, null, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void init() {
        if (genres) {
            MovieService.getInstance().searchByGenre(getActivity(), query, new MovieService.OnSearch() {
                @Override
                public void onSearchFinish(List<MovieSearch> movies) {
                    populateValues(movies);
                }

                @Override
                public void onError() {

                }
            });
        } else {

            MovieService.getInstance().search(getActivity(), query, new MovieService.OnSearch() {
                @Override
                public void onSearchFinish(List<MovieSearch> movies) {
                    populateValues(movies);
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    private void populateValues(List<MovieSearch> movies) {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        SearchAdapter adapter = new SearchAdapter(movies);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setMovieClick(new SearchAdapter.OnMovieClick() {
            @Override
            public void onMovieClick(MovieSearch movieSearch) {
                Util.loadDetail(getActivity(), movieSearch.getId(), isVisible());
            }
        });
    }
}
