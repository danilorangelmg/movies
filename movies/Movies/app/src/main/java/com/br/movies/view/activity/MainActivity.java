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
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.br.movies.R;
import com.br.movies.bo.contract.SetUpActionBar;
import com.br.movies.bo.util.Util;
import com.br.movies.view.fragments.HomeFragment;
import com.br.movies.view.fragments.MenuFragment;
import com.br.movies.view.fragments.SearchFragment;

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
    private SearchView searchView;
    private OnBackPressed onBackPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setupActionBar();
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.logout, R.string.logout);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(true);
        Util.checkPermission(this, new String[]{Manifest.permission.INTERNET});

        initFragment();
        initMenu();
        setupActionBar();
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

    public void replace(Fragment f, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, f).addToBackStack(tag)
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

//    private void setupActionBar() {
//        setupActionBarConfig(this, getSupportActionBar(), toolbar, drawer, toggle);
//        setupActionBar(this, getSupportActionBar(), true);
//    }

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

    private void setupActionBar() {
        setupActionBarConfig(MainActivity.this, getSupportActionBar(), toolbar, drawer, toggle);
        setupActionBar(MainActivity.this, getSupportActionBar(), false);
    }

    @Override
    public void setupActionBar(AppCompatActivity activity, ActionBar actionBar, boolean enableHomeButton) {
        View actionBarLayout = Util.setupActionBar(activity, actionBar, enableHomeButton);

        final TextView labelTitle = (TextView) actionBarLayout.findViewById(R.id.actionTitle);
        labelTitle.setText("FILMES");

        searchView = (SearchView) actionBarLayout.findViewById(R.id.searchView);
        searchView.setQueryHint("Informe o filme...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchFragment fragment = SearchFragment.newInstance(query, false);
                replace(fragment);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                labelTitle.setVisibility(View.GONE);
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelTitle.setVisibility(View.GONE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                labelTitle.setVisibility(View.VISIBLE);
                return false;
            }
        });

        ImageView homeButton = (ImageView) actionBarLayout.findViewById(R.id.homeMenuButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu(drawer);
            }
        });
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
