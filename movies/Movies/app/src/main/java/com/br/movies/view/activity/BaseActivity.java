package com.br.movies.view.activity;

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
import com.br.movies.view.fragments.SearchFragment;

/**
 * Created by danilorangel on 10/06/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements SetUpActionBar {


    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private SearchView searchView;
    private ImageView homeButton;
    private TextView labelTitle;
    private String screnName;

    public void setupActionBar(Toolbar toolbar, DrawerLayout drawer, String screenName) {
        this.toolbar = toolbar;
        this.drawer = drawer;
        this.screnName = screenName;
        setup();
    }

    public void hideMenus() {
        searchView.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);
    }

    public void setScreenName(String screenName) {
        this.screnName = screenName;
        labelTitle.setText(screenName.toUpperCase());

    }

    private void setup() {
        setSupportActionBar(toolbar);
        this.toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.logout, R.string.logout);
        this.drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(true);
        setupActionBar();
    }

    private void setupActionBar() {
        setupActionBarConfig(BaseActivity.this, getSupportActionBar(), toolbar, drawer, toggle);
        setupActionBar(BaseActivity.this, getSupportActionBar(), false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            toggleMenu(drawer);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setupActionBar(AppCompatActivity activity, ActionBar actionBar, boolean enableHomeButton) {
        View actionBarLayout = Util.setupActionBar(activity, actionBar, enableHomeButton);

        labelTitle = (TextView) actionBarLayout.findViewById(R.id.actionTitle);
        labelTitle.setText(screnName.toUpperCase());

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

        homeButton = (ImageView) actionBarLayout.findViewById(R.id.homeMenuButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu(drawer);
            }
        });
    }

    public void setArrowIcon() {
        homeButton.setImageResource(R.drawable.arrows);
        homeButton.animate().rotation(-90).start();
        toggleMenu(drawer);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                setHomeButton();
                homeButton.animate().rotation(0).start();
            }
        });
    }

    public void setHomeButton() {
        homeButton.setImageResource(R.drawable.hamb_menu);
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

}
