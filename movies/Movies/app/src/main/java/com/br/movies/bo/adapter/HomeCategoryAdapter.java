package com.br.movies.bo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.br.movies.R;
import com.br.movies.domain.HomeCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 28/03/17.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder> {

    private List<HomeCategory> cat;
    private Context context;
    private CategoryAdapter.OnMovieClick onMovieClick;

    public HomeCategoryAdapter(List<HomeCategory> cat, Context context) {
        this.cat = cat;
        this.context = context;
    }

    @Override
    public HomeCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category_home, parent, false);
        return new HomeCategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeCategoryViewHolder holder, int position) {
        HomeCategory category = cat.get(position);
        holder.txtName.setText(category.getName());
        CategoryAdapter adapter = new CategoryAdapter(category.getMovies());
        adapter.setMovieClick(onMovieClick);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(mLayoutManager);
        holder.recyclerView.setAdapter(adapter);
//        holder.recyclerView.addItemDecoration(new ItemDecorationLineDivider(getActivity(), ItemDecorationLineDivider.Color.GREY));
    }

    @Override
    public int getItemCount() {
        return cat.size();
    }

    public class HomeCategoryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.recyclerViewCategory)
        RecyclerView recyclerView;

        @Bind(R.id.categoryName)
        TextView txtName;

        public HomeCategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnMovieClick(CategoryAdapter.OnMovieClick onMovieClick) {
        this.onMovieClick = onMovieClick;
    }
}
