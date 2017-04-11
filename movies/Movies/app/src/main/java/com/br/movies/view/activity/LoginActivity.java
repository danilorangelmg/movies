package com.br.movies.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.VolleyError;
import com.br.movies.R;
import com.br.movies.bo.contract.GenericResponse;
import com.br.movies.bo.service.UserService;
import com.br.movies.bo.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.br.movies.bo.contract.SetUpActionBar;

/**
 * Created by danilorangel on 03/04/17.
 */

public class LoginActivity extends AppCompatActivity implements SetUpActionBar {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupActionBar();
        Util.checkPermission(this, new String[]{Manifest.permission.INTERNET});

//        initFragment();
//        initMenu();
    }

    public void init() {
        UserService.getInstance().doLogin(this, "", "", new GenericResponse() {
            @Override
            public void onSucess() {

            }

            @Override
            public void onError(VolleyError volleyError) {
                if (volleyError != null && volleyError.getMessage() != null && volleyError.getMessage().contains("username or password is invalid")) {
                    //direciona cadastro
                }
            }
        });
    }



    private void setupActionBar() {

    }

    @Override
    public void setupActionBar(AppCompatActivity activity, ActionBar actionBar, boolean enableHomeButton) {

    }

    @Override
    public void setupActionBarConfig(AppCompatActivity activity, ActionBar actionBar, Toolbar toolbar, DrawerLayout drawer, ActionBarDrawerToggle toggle) {

    }

    @Override
    public void toggleMenu(DrawerLayout drawer) {

    }
}
