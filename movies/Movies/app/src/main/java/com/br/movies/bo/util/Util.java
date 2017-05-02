package com.br.movies.bo.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.br.movies.MoviesApplication;
import com.br.movies.R;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.domain.HomeCategory;
import com.br.movies.view.activity.MainActivity;
import com.br.movies.view.fragments.MovieDetailFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danilorangel on 02/04/17.
 */

public class Util {

    private static List<HomeCategory> homeCategorys;

    public static void checkPermission(Activity activity, String[] permissions) {
//        Manifest.permission.READ_CONTACTS
        for (String permission : permissions) {

            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        permission)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(activity,
                            new String[]{permission},
                            0);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
    }

    public static void loadDetail(final Context context, final int id, final boolean isVisible) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("movie_id",String.valueOf(id));

            MoviesApplication.getApplication().getServiceUtil().callService(ServiceUrl.GET_MOVIE_DETAIL, Request.Method.GET, params, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    if (isVisible && context != null) {
                        ((MainActivity)context).replace(MovieDetailFragment.newInstance(result.toString()), String.valueOf(id));
                    }
                }

                @Override
                public void onError(String service, VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<HomeCategory> getHomeCategorys() {
        return homeCategorys;
    }

    public static void setHomeCategorys(List<HomeCategory> homeCategorys) {
        Util.homeCategorys = homeCategorys;
    }

    public static void setupActionBarConfig(AppCompatActivity activity, ActionBar actionBar, Toolbar toolbar, DrawerLayout drawer, ActionBarDrawerToggle toggle) {
        activity.setSupportActionBar(toolbar);

        if (toggle == null) {
            return;
        }
        toggle = new ActionBarDrawerToggle(
                activity, drawer, R.string.logout, R.string.logout);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    public static void setupActionBar(AppCompatActivity activity, ActionBar actionBar, boolean enableHomeButton) {

        View actionBarLayout = LayoutInflater.from(activity).inflate(R.layout.layout_action_bar, null);
        if (actionBar != null) {
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER_HORIZONTAL);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(actionBarLayout, params);
            actionBar.show();
        }

        if (enableHomeButton) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
        }

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    public static void toggleMenu(DrawerLayout drawer) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }
}
