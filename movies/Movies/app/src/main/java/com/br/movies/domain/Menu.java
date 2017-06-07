package com.br.movies.domain;

import com.br.movies.bo.contract.MenuClickListener;

/**
 * Created by danilorangel on 03/02/17.
 */

public class Menu {

    private int id;
    private String name;
    private MenuClickListener onClick;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuClickListener getOnClick() {
        return onClick;
    }

    public void setOnClick(MenuClickListener onClick) {
        this.onClick = onClick;
    }
}
