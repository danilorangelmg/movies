package com.br.movies.bo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.br.movies.R;
import com.br.movies.domain.Menu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 03/02/17.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<Menu> menuList;

    public MenuAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public MenuAdapter() {
        this.menuList = new ArrayList<>();
    }

    public void addData(List<Menu> menuList) {
        this.menuList.addAll(menuList);
        notifyDataSetChanged();
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_menu, parent, false);
        return new MenuViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        Menu menu = menuList.get(position);
        holder.menuName.setText(menu.getName());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txtMenuSectionName)
        TextView menuName;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
