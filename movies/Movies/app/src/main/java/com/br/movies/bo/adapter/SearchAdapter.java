package com.br.movies.bo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.br.movies.MoviesApplication;
import com.br.movies.R;
import com.br.movies.domain.MovieSearch;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 28/03/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CategoryViewHolder> {


    private List<MovieSearch> values;
    private OnMovieClick onMovieClick;

    public SearchAdapter(List<MovieSearch> values) {
        MovieSearchComparator comparator = new MovieSearchComparator();
        Collections.sort(values, comparator);
        this.values = values;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        //monta a view
        MovieSearch movieSearch = values.get(position);
        holder.imgPoster.setImageUrl(movieSearch.getPoster_path(), MoviesApplication.getApplication().getImageLoader());
        onClickView(holder.itemView, movieSearch);

    }

    public void onClickView(View view, final MovieSearch movieSearch) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMovieClick != null) {
                    onMovieClick.onMovieClick(movieSearch);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.moviePoster)
        NetworkImageView imgPoster;

        View itemView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public void setMovieClick(OnMovieClick onMovieClick) {
        this.onMovieClick = onMovieClick;
    }

    private class MovieSearchComparator implements Comparator<MovieSearch> {

        @Override
        public int compare(MovieSearch o1, MovieSearch o2) {
            if (o1.getVote_average() > o2.getVote_average()) return -1;
            if (o1.getVote_average() < o2.getVote_average()) return 1;
            return 0;
        }

    }


    public interface OnMovieClick {
        public void onMovieClick(MovieSearch movieSearch);
    }


}
