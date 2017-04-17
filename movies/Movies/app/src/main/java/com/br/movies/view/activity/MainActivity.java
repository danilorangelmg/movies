package com.br.movies.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.br.movies.R;
import com.br.movies.bo.contract.SetUpActionBar;
import com.br.movies.bo.util.Util;
import com.br.movies.view.fragments.HomeFragment;
import com.br.movies.view.fragments.MenuFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 06/01/17.
 */

public class MainActivity extends AppCompatActivity implements SetUpActionBar {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    private ActionBarDrawerToggle toggle;
    private OnBackPressed onBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupActionBar();
        Util.checkPermission(this, new String[]{Manifest.permission.INTERNET});

        initFragment();
        initMenu();
    }

    @Override
    public void onBackPressed() {
        if (onBackPressed != null) {
            onBackPressed.onBack();
        }
        super.onBackPressed();
    }

    private void initFragment() {
        HomeFragment f = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, f).addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void replace(Fragment f) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, f).addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void initMenu() {
        MenuFragment fragment = MenuFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_from_right, R.anim.slide_to_left,
                        R.anim.slide_from_left, R.anim.slide_to_right)
                .replace(R.id.menu_content, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void setupActionBar() {
        setupActionBarConfig(this, getSupportActionBar(), toolbar, drawer, toggle);
        setupActionBar(this, getSupportActionBar(), true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            toggleMenu(drawer);
        }


        return super.onOptionsItemSelected(item);
    }


    public void setOnBackPressed(OnBackPressed onBackPressed) {
        this.onBackPressed = onBackPressed;
    }

    @Override
    public void setupActionBar(AppCompatActivity activity, ActionBar actionBar, boolean enableHomeButton) {
        Util.setupActionBar(activity, actionBar, enableHomeButton);
    }

    @Override
    public void setupActionBarConfig(AppCompatActivity activity, ActionBar actionBar, Toolbar toolbar, DrawerLayout drawer, ActionBarDrawerToggle toggle) {
        Util.setupActionBarConfig(activity, actionBar, toolbar, drawer, toggle);
    }

    @Override
    public void toggleMenu(DrawerLayout drawer) {
        Util.toggleMenu(drawer);
    }

    public interface OnBackPressed {
        public void onBack();
    }

}
