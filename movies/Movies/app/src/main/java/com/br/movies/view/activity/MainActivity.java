package com.br.movies.view.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.br.movies.R;
import com.br.movies.view.fragments.HomeFragment;
import com.br.movies.view.fragments.MenuFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 06/01/17.
 */

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupActionBar();
        initFragment();
        initMenu();
    }

    private void initFragment() {
        HomeFragment f = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, f)
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
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.logout, R.string.logout);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        View actionBarLayout = LayoutInflater.from(this).inflate(R.layout.layout_action_bar, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER_HORIZONTAL);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(actionBarLayout, params);
            actionBar.show();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            toggleMenu();
        }


        return super.onOptionsItemSelected(item);
    }

    public void toggleMenu() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

}
