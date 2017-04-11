package com.br.movies.bo.contract;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by danilorangel on 03/04/17.
 */

public interface SetUpActionBar {

    public void setupActionBar(AppCompatActivity activity, ActionBar actionBar, boolean enableHomeButton);
    public void setupActionBarConfig(AppCompatActivity activity, ActionBar actionBar, Toolbar toolbar, DrawerLayout drawer, ActionBarDrawerToggle toggle);
    public void toggleMenu(DrawerLayout drawer);
}
