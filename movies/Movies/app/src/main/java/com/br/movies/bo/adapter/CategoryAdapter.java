package com.br.movies.bo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.movies.R;

import java.util.List;

/**
 * Created by danilorangel on 28/03/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {


    private List<String> values;

    public CategoryAdapter(List<String> values) {
        this.values = values;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category_item_horizontal, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        //monta a view
        String value = values.get(position);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {


        public CategoryViewHolder(View itemView) {
            super(itemView);
        }
    }


}
