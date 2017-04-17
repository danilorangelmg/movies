package com.br.movies.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.br.movies.MoviesApplication;
import com.br.movies.R;
import com.br.movies.bo.adapter.HomeBannerAdapter;
import com.br.movies.connect.ResultService;
import com.br.movies.connect.ServiceUrl;
import com.br.movies.domain.Banner;
import com.br.movies.view.components.circlepageindicator.CirclePageIndicator;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by danilorangel on 15/04/17.
 */

public class ExplainActivity extends AppCompatActivity {

    @Bind(R.id.bannerOffers)
    ViewPager pager;

    @Bind(R.id.indicatior)
    CirclePageIndicator indicator;

    @Bind(R.id.skipButton)
    CardView skipButton;

    @Bind(R.id.nextButton)
    CardView nextButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);
        init();
    }

    public void init() {
        ButterKnife.bind(this);
        loadBanners();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pager.getAdapter().getCount() == (pager.getCurrentItem()+1)) {
                    Intent intent = new Intent(ExplainActivity.this, OfferActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                pager.setCurrentItem(pager.getCurrentItem() + 1);
            }
        });
    }

    private void loadBanners() {
        try {
            MoviesApplication.getApplication().getServiceUtil().callService(ServiceUrl.GET_BANNERS, Request.Method.GET, new ResultService() {
                @Override
                public void onSucess(String service, JSONObject result) {
                    try {
                        JSONArray banners = result.getJSONArray("banners");
                        Banner[] arrayBanner = new Gson().fromJson(banners.toString(), Banner[].class);
                        List<Banner> listbanners = new ArrayList<Banner>(Arrays.asList(arrayBanner));
                        HomeBannerAdapter bannerAdapter = new HomeBannerAdapter(listbanners);
                        pager.setAdapter(bannerAdapter);
                        indicator.setViewPager(pager);
                    } catch (Exception e) {
                        e.printStackTrace();
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


}
