package com.br.movies.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.br.movies.R;
import com.br.movies.bo.contract.GenericResponse;
import com.br.movies.bo.contract.OfferResponse;
import com.br.movies.bo.contract.SetUpActionBar;
import com.br.movies.bo.service.OfferService;
import com.br.movies.bo.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by danilorangel on 16/04/17.
 */

public class OfferActivity extends AppCompatActivity implements SetUpActionBar {

    @Bind(R.id.drawer_offer_layout)
    DrawerLayout drawer;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.txtMovieValue)
    EditText txtMovieValue;

    @Bind(R.id.valueSeekBar)
    SeekBar valueSeekBar;

    @Bind(R.id.txtOfferValue)
    TextView txtOfferValue;

    @Bind(R.id.btnConfirm)
    CardView cardView;

    @Bind(R.id.chbAcceptTerms)
    CheckBox chbAcceptTerms;

    private double movieValue;
    private int movieCount;
    private String price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        init();
    }

    public void init() {
        ButterKnife.bind(this);
        setupActionBar();
        loadMovieValue();
        valueSeekBar.incrementProgressBy(1);
        valueSeekBar.setMax(200);
        valueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               populateValues(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        valueSeekBar.setProgress(1);
    }

    public void loadMovieValue() {
        OfferService.getInstance().getMovieValue(this, new OfferResponse() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    movieValue = response.getDouble("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }

    public void populateValues(int qtd) {
        if (qtd == 0) {
            qtd = 1;
        }
        movieCount = qtd;
        txtMovieValue.setText(String.valueOf(qtd));
        Double valuePlan = movieValue * qtd;
        BigDecimal bigDecimal = new BigDecimal(valuePlan);
        price = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
        txtOfferValue.setText(price.concat(" por mês"));
    }

    @OnClick(R.id.btnConfirm)
    public void clickContinue() {
        sendOffer();
    }

    public void sendOffer() {
        OfferService.getInstance().sendNewOffer(this, movieCount, price, new GenericResponse() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(OfferActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }

    public void setupActionBar() {
        setupActionBarConfig(this, getSupportActionBar(), toolbar, drawer, null);
        setupActionBar(this, getSupportActionBar(), false);
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
        //nao faz nada
    }
}
