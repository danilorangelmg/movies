package com.br.movies.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.br.movies.R;
import com.br.movies.bo.util.Util;
import com.br.movies.view.fragments.HomeFragment;
import com.br.movies.view.fragments.MenuFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 06/01/17.
 */

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    private OnBackPressed onBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Util.checkPermission(this, new String[]{Manifest.permission.INTERNET});

        initFragment();
        initMenu();
        setupActionBar(toolbar, drawer, "Filmes");
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

    public void setOnBackPressed(OnBackPressed onBackPressed) {
        this.onBackPressed = onBackPressed;
    }

    public interface OnBackPressed {
        public void onBack();
    }

}
